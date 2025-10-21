package tec.br.opticlinic.api.application.auth;

import tec.br.opticlinic.api.web.dto.request.LoginRequest;
import tec.br.opticlinic.api.web.dto.response.TokenResponse;

import java.util.Optional;

public interface AuthLoginService {
    Optional<TokenResponse> login(LoginRequest request);
}
