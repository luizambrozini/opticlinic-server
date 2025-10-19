package tec.br.opticlinic.api.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class CompanyShortResponse {
    private String name;
    private String cnpj;
}
