package tec.br.opticlinic.api.web.error;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AppException {
    public BadRequestException(ErrorCode code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}