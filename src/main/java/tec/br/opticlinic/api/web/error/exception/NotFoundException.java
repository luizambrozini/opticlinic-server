package tec.br.opticlinic.api.web.error.exception;

import org.springframework.http.HttpStatus;
import tec.br.opticlinic.api.web.error.AppException;
import tec.br.opticlinic.api.web.error.ErrorCode;

public class NotFoundException extends AppException {
    public NotFoundException(ErrorCode code, String message) {
        super(HttpStatus.NOT_FOUND, code, message);
    }
}