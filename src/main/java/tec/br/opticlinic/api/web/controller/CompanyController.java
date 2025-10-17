package tec.br.opticlinic.api.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tec.br.opticlinic.api.application.clinic.UpdateCompanyDataService;
import tec.br.opticlinic.api.web.dto.request.UpdateCompanyRequest;

@RestController
@RequestMapping(value = "/api/company", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CompanyController {

    private final UpdateCompanyDataService updateCompanyDataService;

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCompany(@Valid @RequestBody UpdateCompanyRequest body) throws Exception {
        updateCompanyDataService.execute(body.getName(), body.getCnpj());
        return ResponseEntity.noContent().build(); // 204
    }
}
