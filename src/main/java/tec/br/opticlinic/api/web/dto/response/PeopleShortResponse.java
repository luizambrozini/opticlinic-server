package tec.br.opticlinic.api.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PeopleShortResponse {
    private Long id;
    private String name;
    private String cpf;
}
