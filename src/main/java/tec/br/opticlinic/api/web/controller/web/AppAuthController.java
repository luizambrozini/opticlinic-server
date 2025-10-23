package tec.br.opticlinic.api.web.controller.web;

import org.apache.coyote.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppAuthController {

    @GetMapping("/app/auth/login")
    public String  redirectToHome() {
        return "auth/login";
    }
}
