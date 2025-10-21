package tec.br.opticlinic.api.application.clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tec.br.opticlinic.api.application.util.DocumentUtilService;
import tec.br.opticlinic.api.infra.model.Company;
import tec.br.opticlinic.api.infra.repository.CompanyRepository;
import tec.br.opticlinic.api.web.error.exception.BadRequestException;
import tec.br.opticlinic.api.web.error.ErrorCode;
import tec.br.opticlinic.api.web.error.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class UpdateCompanyDataService {

    private final CompanyRepository companyRepository;
    private final DocumentUtilService documentUtilService;

    public void execute(String name, String cnpj) throws Exception {
        var companyOptional = companyRepository.findById(1L);
        if (companyOptional.isEmpty()) {
            throw new NotFoundException(ErrorCode.COMPANY_NOT_FOUND, "Empresa (id=1) não encontrada.");
        }

        Company company = companyOptional.get();
        changeName(company, name);
        changeCnpj(company, cnpj);

        companyRepository.save(company);
    }

    private void changeName(Company company, String newName) {
        if (company.getName() == null || !company.getName().equals(newName)) {
            company.setName(newName);
        }
    }

    private void changeCnpj(Company company, String newCnpj) {
        if (company.getCnpj() == null || !company.getCnpj().equals(newCnpj)) {
            if (!documentUtilService.verifyCnpj(newCnpj)) {
                throw new BadRequestException(ErrorCode.VALIDATION_ERROR,"CNPJ inválido.");
            }
            company.setCnpj(newCnpj);
        }
    }
}
