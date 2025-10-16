package tec.br.opticlinic.api.infra.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
public class Company {
    private Long id;
    private String name;
    private String cnpj;
    private OffsetDateTime created_at;
}
