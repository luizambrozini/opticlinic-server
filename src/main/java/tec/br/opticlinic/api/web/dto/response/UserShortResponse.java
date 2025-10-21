package tec.br.opticlinic.api.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class UserShortResponse {
    private Long id;
    private String username;
    private Boolean enabled;
}
