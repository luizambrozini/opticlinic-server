package tec.br.opticlinic.api.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.OffsetDateTime;

@Entity
@Table(name = "app_company")
@Getter @Setter
public class Company {
    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(length = 20, nullable = false, unique = true) // CNPJ costuma ter 14 dígitos (sem máscara)
    private String cnpj;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Version
    private Long version;
}
