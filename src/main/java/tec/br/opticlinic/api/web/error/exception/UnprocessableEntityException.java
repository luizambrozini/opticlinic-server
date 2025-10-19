package tec.br.opticlinic.api.web.error.exception;

import org.springframework.http.HttpStatus;
import tec.br.opticlinic.api.web.error.AppException;
import tec.br.opticlinic.api.web.error.ErrorCode;

public class UnprocessableEntityException extends AppException {
    public UnprocessableEntityException(ErrorCode code, String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, code, message);
    }
}