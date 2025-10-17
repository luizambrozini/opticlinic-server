package tec.br.opticlinic.api.infra.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthority {
    private Long id;
    private String username;
    private String authority;
}
