package tec.br.opticlinic.api.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tec.br.opticlinic.api.application.clinic.GetCompanyShortService;
import tec.br.opticlinic.api.application.clinic.UpdateCompanyDataService;
import tec.br.opticlinic.api.web.dto.request.UpdateCompanyRequest;
import tec.br.opticlinic.api.web.dto.response.CompanyShortResponse;

@RestController
@RequestMapping(value = "/api/company", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CompanyController {

    private final UpdateCompanyDataService updateCompanyDataService;
    private final GetCompanyShortService getCompanyShortService;

    @GetMapping(value = "get-company")
    public ResponseEntity<CompanyShortResponse> getCompany() throws Exception {
        var response = getCompanyShortService.execute();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update-company", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCompany(@Valid @RequestBody UpdateCompanyRequest body) throws Exception {
        updateCompanyDataService.execute(body.getName(), body.getCnpj());
        return ResponseEntity.noContent().build(); // 204
    }
}
