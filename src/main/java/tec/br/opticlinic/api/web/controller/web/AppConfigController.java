package tec.br.opticlinic.api.web.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.br.opticlinic.api.application.clinic.GetCompanyShortService;
import tec.br.opticlinic.api.application.clinic.UpdateCompanyDataService;
import tec.br.opticlinic.api.application.user.get_user_list.GetUserListService;
import tec.br.opticlinic.api.infra.model.Company;

@Controller
public class AppConfigController {

    @Autowired
    private GetCompanyShortService getCompanyShortService;

    @Autowired
    private UpdateCompanyDataService companyUpdateService;

    @Autowired
    private GetUserListService getUserListService;

    @GetMapping("/app/config")
    public String  redirectToHome(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageUser,
            @RequestParam(defaultValue = "10") Integer limitUser
    ) {
        var company = getCompanyShortService.executeApp();
        var usersAndPagination = getUserListService.executeApp(pageUser, limitUser);
        model.addAttribute("company", company);
        model.addAttribute("usersAndPagination", usersAndPagination);

        // company.getCreatedAt() é OffsetDateTime
        if (company.getCreatedAt() != null) {
            java.util.Date createdAtDate = java.util.Date.from(company.getCreatedAt().toInstant());
            model.addAttribute("createdAtDate", createdAtDate);
        }

        return "config/index"; // /WEB-INF/jsp/config/index.jsp
    }

    @GetMapping("/app/config/company-data")
    public ResponseEntity<Company> getCompanyData() {
        var company = getCompanyShortService.executeApp();
        return ResponseEntity.ok(company);
    }

    @PostMapping("/app/config/save-company")
    public String saveCompany(
            @RequestParam String name,
            @RequestParam String cnpj,
            RedirectAttributes ra
    ) {
        try {
            companyUpdateService.execute(name, cnpj);
            ra.addAttribute("ok", 1);
            return "redirect:/app/config";
        } catch (Exception e) {
            ra.addAttribute("err", "CD01");
            return "redirect:/app/config";
        }
    }
}
