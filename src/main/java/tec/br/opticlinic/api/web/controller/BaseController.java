package tec.br.opticlinic.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import tec.br.opticlinic.api.security.JwtUtil;

public class BaseController {
    @Autowired
    private JwtUtil jwtUtil;

    public String extractUsernameToken(String authorizationHeader) {
        var token = authorizationHeader.replace("Bearer ", "");
        return jwtUtil.getUsernameFromToken(token);
    }
}
