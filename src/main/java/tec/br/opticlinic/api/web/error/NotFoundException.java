package tec.br.opticlinic.api.web.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException {
    public NotFoundException(ErrorCode code, String message) {
        super(HttpStatus.NOT_FOUND, code, message);
    }

/*
    public class ConflictException extends AppException {
        public ConflictException(ErrorCode code, String message) {
            super(HttpStatus.CONFLICT, code, message);
        }
    }

    public class BadRequestException extends AppException {
        public BadRequestException(ErrorCode code, String message) {
            super(HttpStatus.BAD_REQUEST, code, message);
        }
    }

    public class UnprocessableEntityException extends AppException {
        public UnprocessableEntityException(ErrorCode code, String message) {
            super(HttpStatus.UNPROCESSABLE_ENTITY, code, message);
        }
    }
 */
}