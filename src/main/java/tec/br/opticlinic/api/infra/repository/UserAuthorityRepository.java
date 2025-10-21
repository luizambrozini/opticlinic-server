package tec.br.opticlinic.api.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tec.br.opticlinic.api.infra.model.User;
import tec.br.opticlinic.api.infra.model.UserAuthority;

import java.util.List;
import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    boolean existsByUserAndAuthority(User user, String authority);
    List<String> findByUser(User user);
}