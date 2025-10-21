package tec.br.opticlinic.api.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(
        name = "app_user_authority",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_authority",
                columnNames = {"user_id", "authority"}
        )
)
@Getter @Setter
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK para app_user.id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String authority;
}
