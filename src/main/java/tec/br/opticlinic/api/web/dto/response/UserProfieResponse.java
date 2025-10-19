package tec.br.opticlinic.api.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter @AllArgsConstructor
public class UserProfieResponse {
    private String usarname;
    private OffsetDateTime createdAt;
}
