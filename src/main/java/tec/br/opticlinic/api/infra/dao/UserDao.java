package tec.br.opticlinic.api.infra.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tec.br.opticlinic.api.application.util.StringUtilService;
import tec.br.opticlinic.api.infra.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
@AllArgsConstructor
@Slf4j
public class UserDao {
    private final JdbcTemplate jdbc;
    private final StringUtilService stringUtilService;

    private final RowMapper<User> mapper = (rs, rowNum) -> {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setEnabled(rs.getBoolean("enabled"));
        // Se a coluna for TIMESTAMP WITH TIME ZONE (timestamptz):
        u.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));
        // Se for TIMESTAMP sem TZ, use: rs.getTimestamp("created_at").toLocalDateTime()
        return u;
    };

    public Optional<User> findById(Long id) {
        String sql = "SELECT id, username, password, enabled, created_at FROM app_user WHERE id = ?";
        log.info(stringUtilService.formatSql(sql));
        return jdbc.query(sql, mapper, id).stream().findFirst();
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, password, enabled, created_at FROM app_user WHERE username = ?";
        log.info(stringUtilService.formatSql(sql));
        return jdbc.query(sql, mapper, username).stream().findFirst();
    }

    public List<User> findAll(int limit, int offset) {
        String sql = """
            SELECT id, username, password, enabled, created_at
              FROM app_user
             ORDER BY id DESC
             LIMIT ? OFFSET ?
        """;
        log.info(stringUtilService.formatSql(sql));
        return jdbc.query(sql, mapper, limit, offset);
    }

    // ============================
    // Opção A) INSERT com RETURNING (Postgres)
    // ============================
    public Long insert(User u) {
        String sql = """
            INSERT INTO app_user (username, password, enabled)
            VALUES (?, ?, COALESCE(?, TRUE))
            RETURNING id
        """;
        // 3 parâmetros: username, password, enabled
        log.info(stringUtilService.formatSql(sql));
        return jdbc.queryForObject(sql, Long.class, u.getUsername(), u.getPassword(), u.getEnabled());
    }

    public int update(User u) {
        // Se não quiser permitir troca de username, remova-o do SET
        String sql = """
            UPDATE app_user
               SET username = ?,
                   password = ?,
                   enabled  = COALESCE(?, enabled)
             WHERE id = ?
        """;
        log.info(stringUtilService.formatSql(sql));
        return jdbc.update(sql, u.getUsername(), u.getPassword(), u.getEnabled(), u.getId());
    }

    // Corrigido: nome e semântica alinham
    public int deleteById(Long id) {
        String sql = "DELETE FROM app_user WHERE id = ?";
        log.info(stringUtilService.formatSql(sql));
        return jdbc.update(sql, id);
    }

    // Se quiser deletar por username, use este:
    public int deleteByUsername(String username) {
        String sql = "DELETE FROM app_user WHERE username = ?";
        log.info(stringUtilService.formatSql(sql));
        return jdbc.update(sql, username);
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT EXISTS (SELECT 1 FROM app_user WHERE username = ?)";
        log.info(stringUtilService.formatSql(sql));
        Boolean exists = jdbc.queryForObject(sql, Boolean.class, username);
        return Boolean.TRUE.equals(exists);
    }
}
