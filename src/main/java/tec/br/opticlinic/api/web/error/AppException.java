package tec.br.opticlinic.api.web.error;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class AppException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode code;
    private final Map<String, Object> details;

    public AppException(HttpStatus status, ErrorCode code, String message) {
        this(status, code, message, null);
    }

    public AppException(HttpStatus status, ErrorCode code, String message, Map<String, Object> details) {
        super(message);
        this.status = status;
        this.code = code;
        this.details = details;
    }

    public HttpStatus getStatus() { return status; }
    public ErrorCode getCode() { return code; }
    public Map<String, Object> getDetails() { return details; }
}
