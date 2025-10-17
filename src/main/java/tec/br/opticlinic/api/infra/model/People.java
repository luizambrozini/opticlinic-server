package tec.br.opticlinic.api.infra.model;

import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
public class People {
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private OffsetDateTime createdAt;
    private Long companyId; // FK -> app_company.id
}
