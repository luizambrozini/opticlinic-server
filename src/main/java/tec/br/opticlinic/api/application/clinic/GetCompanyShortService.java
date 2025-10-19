package tec.br.opticlinic.api.application.clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tec.br.opticlinic.api.infra.dao.CompanyDao;
import tec.br.opticlinic.api.infra.model.Company;
import tec.br.opticlinic.api.web.dto.response.CompanyShortResponse;
import tec.br.opticlinic.api.web.error.ErrorCode;
import tec.br.opticlinic.api.web.error.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class GetCompanyShortService {

    private final CompanyDao companyDao;

    public CompanyShortResponse execute() {
        var companyOptional = companyDao.findById(1L);
        if (companyOptional.isEmpty()) {
            throw new NotFoundException(ErrorCode.COMPANY_NOT_FOUND, "Empresa (id=1) não encontrada.");
        }

        Company company = companyOptional.get();

        return new CompanyShortResponse(
                company.getName(),
                company.getCnpj()
        );
    }
}
