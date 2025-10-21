package tec.br.opticlinic.api.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.br.opticlinic.api.infra.model.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);
}
