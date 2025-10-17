package tec.br.opticlinic.api.application.clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tec.br.opticlinic.api.infra.dao.CompanyDao;
import tec.br.opticlinic.api.infra.model.Company;

@Service
@RequiredArgsConstructor
public class UpdateCompanyDataService {

    private final CompanyDao companyDao;

    public void execute(String name, String cnpj) throws Exception {
        var companyOptional = companyDao.findById(1L);
        if (companyOptional.isEmpty()) {
            throw new Exception("Company not found!");
        }

        Company company = companyOptional.get();
        changeName(company, name);
        changeCnpj(company, cnpj);

        companyDao.update(company);
    }

    private void changeName(Company company, String newName) {
        if (company.getName() == null || !company.getName().equals(newName)) {
            company.setName(newName);
        }
    }

    private void changeCnpj(Company company, String newCnpj) {
        if (company.getCnpj() == null || !company.getCnpj().equals(newCnpj)) {
            company.setCnpj(newCnpj);
        }
    }
}
