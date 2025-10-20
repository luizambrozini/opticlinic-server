package tec.br.opticlinic.api.infra.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tec.br.opticlinic.api.infra.model.People;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class PeopleDao {

    private final JdbcTemplate jdbc;

    public PeopleDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<People> mapper = (rs, rowNum) -> {
        People p = new People();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setCpf(rs.getString("cpf"));
        p.setEmail(rs.getString("email"));
        p.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));
        Long companyId = rs.getObject("company_id") == null ? null : rs.getLong("company_id");
        //p.setCompanyId(companyId);
        return p;
    };

    public Optional<People> findById(Long id) {
        String sql = """
            SELECT id, name, cpf, email, created_at, company_id
              FROM app_people
             WHERE id = ?
        """;
        return jdbc.query(sql, mapper, id).stream().findFirst();
    }

    public List<People> findByCompanyId(Long companyId, int limit, int offset) {
        String sql = """
            SELECT id, name, cpf, email, created_at, company_id
              FROM app_people
             WHERE company_id = ?
             ORDER BY id DESC
             LIMIT ? OFFSET ?
        """;
        return jdbc.query(sql, mapper, companyId, limit, offset);
    }

    public Optional<People> findByCpf(String cpf) {
        String sql = """
            SELECT id, name, cpf, email, created_at, company_id
              FROM app_people
             WHERE cpf = ?
        """;
        return jdbc.query(sql, mapper, cpf).stream().findFirst();
    }

    public Optional<People> findByEmail(String email) {
        String sql = """
            SELECT id, name, cpf, email, created_at, company_id
              FROM app_people
             WHERE email = ?
        """;
        return jdbc.query(sql, mapper, email).stream().findFirst();
    }

    public List<People> findAll(int limit, int offset) {
        String sql = """
            SELECT id, name, cpf, email, created_at, company_id
              FROM app_people
             ORDER BY id DESC
             LIMIT ? OFFSET ?
        """;
        return jdbc.query(sql, mapper, limit, offset);
    }

    public Long insert(People p) {
        String sql = """
            INSERT INTO app_people (name, cpf, email, company_id)
            VALUES (?, ?, ?, ?)
            RETURNING id
        """;
        return jdbc.queryForObject(sql, Long.class,
                p.getName(), p.getCpf(), p.getEmail());
    }

    public int update(People p) {
        String sql = """
            UPDATE app_people
               SET name = ?,
                   cpf = ?,
                   email = ?,
                   company_id = ?
             WHERE id = ?
        """;
        return jdbc.update(sql,
                p.getName(), p.getCpf(), p.getEmail(), p.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM app_people WHERE id = ?";
        return jdbc.update(sql, id);
    }

    public boolean existsByCpf(String cpf) {
        String sql = "SELECT EXISTS (SELECT 1 FROM app_people WHERE cpf = ?)";
        Boolean exists = jdbc.queryForObject(sql, Boolean.class, cpf);
        return Boolean.TRUE.equals(exists);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS (SELECT 1 FROM app_people WHERE email = ?)";
        Boolean exists = jdbc.queryForObject(sql, Boolean.class, email);
        return Boolean.TRUE.equals(exists);
    }
}
