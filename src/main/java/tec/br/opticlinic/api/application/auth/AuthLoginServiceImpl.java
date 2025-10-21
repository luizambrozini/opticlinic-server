package tec.br.opticlinic.api.application.auth;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tec.br.opticlinic.api.infra.repository.UserRepository;
import tec.br.opticlinic.api.security.JwtUtil;
import tec.br.opticlinic.api.web.dto.request.LoginRequest;
import tec.br.opticlinic.api.web.dto.response.TokenResponse;

import java.util.Optional;


@Service
public class AuthLoginServiceImpl implements AuthLoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional()
    public Optional<TokenResponse> login(LoginRequest request) {
        var userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty() || !Boolean.TRUE.equals(userOpt.get().getEnabled())) {
            return Optional.empty();
        }

        var user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return Optional.empty();
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return Optional.of(new TokenResponse(token, "Bearer"));
    }
}
