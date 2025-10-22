package tec.br.opticlinic.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tec.br.opticlinic.api.application.auth.GetUserProfileService;
import tec.br.opticlinic.api.application.user.get_user_list.GetUserListService;
import tec.br.opticlinic.api.web.dto.response.UserListResponse;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController {
    @Autowired
    private GetUserProfileService getUserProfileService;

    @Autowired
    private GetUserListService userListService;

    @GetMapping(value = "user-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserListResponse> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        var response = userListService.execute(page, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "user-profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader ("Authorization") String authorizationHeader) {
        var response = getUserProfileService.execute(extractUsernameToken(authorizationHeader));
        return ResponseEntity.ok(response);
    }



}
