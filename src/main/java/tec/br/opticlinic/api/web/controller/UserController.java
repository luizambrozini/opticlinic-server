package tec.br.opticlinic.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tec.br.opticlinic.api.application.auth.GetUserProfileService;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController {
    @Autowired
    private GetUserProfileService getUserProfileService;

    @GetMapping(value = "user-profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader ("Authorization") String authorizationHeader) {
        var response = getUserProfileService.execute(extractUsernameToken(authorizationHeader));
        return ResponseEntity.ok(response);
    }



}
