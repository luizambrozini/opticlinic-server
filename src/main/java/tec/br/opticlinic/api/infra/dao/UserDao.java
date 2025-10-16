package tec.br.opticlinic.api.infra.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tec.br.opticlinic.api.infra.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class UserDao {
    private final JdbcTemplate jdbc;

    public UserDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private User map(ResultSet rs, int rowNum) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setEnabled(rs.getBoolean("enabled"));
        u.setCreatedAt(rs.getObject("created_at", java.time.OffsetDateTime.class));
        return u;
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM app_user WHERE id = ?";
        List<User> list = jdbc.query(sql, this::map, id);
        return list.stream().findFirst();
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM app_user WHERE username = ?";
        List<User> list = jdbc.query(sql, this::map, username);
        return list.stream().findFirst();
    }

    public List<User> findAll(int limit, int offset) {
        String sql = "SELECT * FROM app_user ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, this::map, limit, offset);
    }

    public Long insert(User u) {
        String sql = """
            INSERT INTO app_user (username, full_name, email, enabled)
            VALUES (?, ?, ?, COALESCE(?, TRUE))
            """;

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getFullName());
            ps.setString(3, u.getEmail());
            if (u.getEnabled() == null) ps.setObject(4, null);
            else ps.setBoolean(4, u.getEnabled());
            return ps;
        }, kh);

        Number key = kh.getKey();
        return key != null ? key.longValue() : null;
    }

    public int update(User u) {
        String sql = """
            UPDATE app_user
               SET full_name = ?, email = ?, enabled = COALESCE(?, enabled)
             WHERE username = ?
            """;
        return jdbc.update(sql, u.getFullName(), u.getEmail(), u.getEnabled(), u.getUsername());
    }

    public int deleteByUsername(String username) {
        String sql = "DELETE FROM app_user WHERE username = ?";
        return jdbc.update(sql, username);
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT EXISTS (SELECT 1 FROM app_user WHERE username = ?)";
        Boolean exists = jdbc.queryForObject(sql, Boolean.class, username);
        return Boolean.TRUE.equals(exists);
    }
}
