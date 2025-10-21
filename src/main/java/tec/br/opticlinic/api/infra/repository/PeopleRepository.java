package tec.br.opticlinic.api.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tec.br.opticlinic.api.infra.model.People;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People, Long> {
    Optional<People> findByCpf(String cpf);
    Optional<People> findByEmail(String email);
    Page<People> findByCompany_Id(Long companyId, Pageable pageable);
}
