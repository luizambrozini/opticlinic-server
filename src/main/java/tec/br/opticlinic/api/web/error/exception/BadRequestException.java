package tec.br.opticlinic.api.web.error.exception;

import org.springframework.http.HttpStatus;
import tec.br.opticlinic.api.web.error.AppException;
import tec.br.opticlinic.api.web.error.ErrorCode;

public class BadRequestException extends AppException {
    public BadRequestException(ErrorCode code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}