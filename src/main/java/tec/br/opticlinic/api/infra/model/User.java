package tec.br.opticlinic.api.infra.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
public class User {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Boolean enabled;
    private OffsetDateTime createdAt;
}
