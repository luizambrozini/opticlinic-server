package tec.br.opticlinic.api.web.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private final String tokenType;
}
