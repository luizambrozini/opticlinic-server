package tec.br.opticlinic.api.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tec.br.opticlinic.api.infra.repository.UserRepository;
import tec.br.opticlinic.api.security.JwtUtil;
import tec.br.opticlinic.api.web.dto.request.LoginRequest;
import tec.br.opticlinic.api.web.dto.response.TokenResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {


        var userOptional = userRepository.findByUsername(request.getUsername());

        if(userOptional.isEmpty() || !userOptional.get().getEnabled()) {
            return ResponseEntity.status(401).build();
        }

        var user = userOptional.get();

        var isValidPassword = new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword());

        if(!isValidPassword) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
    }
}
