package tec.br.opticlinic.api.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import tec.br.opticlinic.api.infra.dao.UserDao;
import tec.br.opticlinic.api.security.JwtService;
import tec.br.opticlinic.api.web.dto.request.LoginRequest;
import tec.br.opticlinic.api.web.dto.response.TokenResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDao userDao;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDao = userDao;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );



        var userOptional = userDao.findByUsername(request.getUsername());

        if(userOptional.isEmpty() || !userOptional.get().getEnabled()) {
            return ResponseEntity.status(401).build();
        }

        Map<String, Object> claims = new HashMap<>();
        String roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("roles", roles);

        var user = userOptional.get();

        claims.put("username", user.getUsername());
        String token = jwtService.generateToken(user.getId(), claims);
        return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
    }
}
