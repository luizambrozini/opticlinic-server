package tec.br.opticlinic.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tec.br.opticlinic.api.application.auth.GetUserProfileService;
import tec.br.opticlinic.api.security.JwtService;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final JwtService jwtService;
    private final GetUserProfileService getUserProfileService;

    @GetMapping(value = "user-profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader ("Authorization") String authorizationHeader) {
        var response = getUserProfileService.execute(extractSubjectToken(authorizationHeader));
        return ResponseEntity.ok(response);
    }

    private Long extractSubjectToken(String authorizationHeader) {
        // The subject are the user id
        var token = authorizationHeader.replace("Bearer ", "");
        return jwtService.extractSubject(token);
    }

}
