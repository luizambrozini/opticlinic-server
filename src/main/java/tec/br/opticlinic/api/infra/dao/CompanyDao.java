package tec.br.opticlinic.api.infra.dao;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tec.br.opticlinic.api.application.util.StringUtilService;
import tec.br.opticlinic.api.infra.model.Company;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class CompanyDao {

    private final JdbcTemplate jdbc;
    private final StringUtilService stringUtilService;

    private final RowMapper<Company> mapper = (rs, rowNum) -> {
        Company c = new Company();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        c.setCnpj(rs.getString("cnpj"));
        c.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));
        return c;
    };

    public Optional<Company> findById(Long id) {
        String sql = """
            SELECT id, name, cnpj, created_at
              FROM app_company
             WHERE id = ?
        """;
        log.info(stringUtilService.formatSql(sql));
        return jdbc.query(sql, mapper, id).stream().findFirst();
    }

    public Optional<Company> findByCnpj(String cnpj) {
        String sql = """
            SELECT id, name, cnpj, created_at
              FROM app_company
             WHERE cnpj = ?
        """;
        log.info(stringUtilService.formatSql(sql));
        return jdbc.query(sql, mapper, cnpj).stream().findFirst();
    }

    public List<Company> findAll(int limit, int offset) {
        String sql = """
            SELECT id, name, cnpj, created_at
              FROM app_company
             ORDER BY id DESC
             LIMIT ? OFFSET ?
        """;
        log.info(stringUtilService.formatSql(sql));
        return jdbc.query(sql, mapper, limit, offset);
    }

    public Long insert(Company c) {
        String sql = """
            INSERT INTO app_company (name, cnpj)
            VALUES (?, ?)
            RETURNING id
        """;
        log.info(stringUtilService.formatSql(sql));
        return jdbc.queryForObject(sql, Long.class, c.getName(), c.getCnpj());
    }

    public int update(Company c) {
        String sql = """
            UPDATE app_company
               SET name = ?,
                   cnpj = ?
             WHERE id = ?
        """;
        log.info(stringUtilService.formatSql(sql));
        return jdbc.update(sql, c.getName(), c.getCnpj(), c.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM app_company WHERE id = ?";
        log.info(stringUtilService.formatSql(sql));
        return jdbc.update(sql, id);
    }

    public boolean existsByCnpj(String cnpj) {
        String sql = "SELECT EXISTS (SELECT 1 FROM app_company WHERE cnpj = ?)";
        log.info(stringUtilService.formatSql(sql));
        Boolean exists = jdbc.queryForObject(sql, Boolean.class, cnpj);
        return Boolean.TRUE.equals(exists);
    }


}
