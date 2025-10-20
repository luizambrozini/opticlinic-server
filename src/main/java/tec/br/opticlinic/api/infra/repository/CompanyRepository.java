package tec.br.opticlinic.api.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.br.opticlinic.api.infra.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
