package tec.br.opticlinic.api.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "app_company")
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(length = 50, nullable = false)
    private String cnpj;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
