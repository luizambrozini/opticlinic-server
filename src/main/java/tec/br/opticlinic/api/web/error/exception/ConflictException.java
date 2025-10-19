package tec.br.opticlinic.api.web.error.exception;

import org.springframework.http.HttpStatus;
import tec.br.opticlinic.api.web.error.AppException;
import tec.br.opticlinic.api.web.error.ErrorCode;

public class ConflictException extends AppException {
    public ConflictException(ErrorCode code, String message) {
        super(HttpStatus.CONFLICT, code, message);
    }
}