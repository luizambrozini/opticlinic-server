package tec.br.opticlinic.api.infra.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tec.br.opticlinic.api.infra.model.UserAuthority;

import java.util.List;
import java.util.Optional;

@Repository
public class UserAuthorityDao {

    private final JdbcTemplate jdbc;

    public UserAuthorityDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<UserAuthority> mapper = (rs, rowNum) -> {
        UserAuthority a = new UserAuthority();
        a.setId(rs.getLong("id"));
        a.setUsername(rs.getString("username"));
        a.setAuthority(rs.getString("authority"));
        return a;
    };

    public Optional<UserAuthority> findById(Long id) {
        String sql = """
            SELECT id, username, authority
              FROM app_user_authority
             WHERE id = ?
        """;
        return jdbc.query(sql, mapper, id).stream().findFirst();
    }

    public List<UserAuthority> findByUsername(String username) {
        String sql = """
            SELECT id, username, authority
              FROM app_user_authority
             WHERE username = ?
             ORDER BY authority
        """;
        return jdbc.query(sql, mapper, username);
    }

    public boolean exists(String username, String authority) {
        String sql = """
            SELECT EXISTS(
                SELECT 1
                  FROM app_user_authority
                 WHERE username = ?
                   AND authority = ?
            )
        """;
        Boolean exists = jdbc.queryForObject(sql, Boolean.class, username, authority);
        return Boolean.TRUE.equals(exists);
    }

    public Long insert(String username, String authority) {
        String sql = """
            INSERT INTO app_user_authority (username, authority)
            VALUES (?, ?)
            RETURNING id
        """;
        return jdbc.queryForObject(sql, Long.class, username, authority);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM app_user_authority WHERE id = ?";
        return jdbc.update(sql, id);
    }

    public int deleteByUsernameAndAuthority(String username, String authority) {
        String sql = """
            DELETE FROM app_user_authority
             WHERE username = ?
               AND authority = ?
        """;
        return jdbc.update(sql, username, authority);
    }
}
