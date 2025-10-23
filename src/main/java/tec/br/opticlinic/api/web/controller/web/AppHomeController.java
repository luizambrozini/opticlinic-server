package tec.br.opticlinic.api.web.controller.web;

import org.apache.coyote.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppHomeController {

    @GetMapping("/")
    public String  redirectToHome() {
        return "redirect:/app/home";
    }

    @GetMapping("/app")
    public String app(Model model) {
        model.addAttribute("nome", "Luiz");
        return "home"; // /WEB-INF/jsp/home.jsp
    }

}
