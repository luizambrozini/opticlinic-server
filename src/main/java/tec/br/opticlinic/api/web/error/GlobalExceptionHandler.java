package tec.br.opticlinic.api.web.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // === AppException base ===
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ProblemDetail> handleApp(AppException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
        pd.setTitle(ex.getCode().name());
        pd.setProperty("code", ex.getCode().name());
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", OffsetDateTime.now());
        if (ex.getDetails() != null) pd.setProperty("details", ex.getDetails());
        return ResponseEntity.status(ex.getStatus()).body(pd);
    }

    // === Bean Validation @Valid (JSON body) ===
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        ProblemDetail pd = ProblemDetail.forStatus(400);
        pd.setTitle(ErrorCode.VALIDATION_ERROR.name());
        pd.setDetail("Falha de validação do payload.");
        pd.setProperty("code", ErrorCode.VALIDATION_ERROR.name());
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", OffsetDateTime.now());
        pd.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(pd);
    }

    // === Bean Validation em params/path (@Validated @PathVariable/@RequestParam) ===
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(400);
        pd.setTitle(ErrorCode.CONSTRAINT_VIOLATION.name());
        pd.setDetail("Parâmetros inválidos.");
        pd.setProperty("code", ErrorCode.CONSTRAINT_VIOLATION.name());
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", OffsetDateTime.now());
        pd.setProperty("violations", ex.getConstraintViolations());
        return ResponseEntity.badRequest().body(pd);
    }

    // === Violação de integridade (índice único, FK, etc.) ===
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(409);
        pd.setTitle(ErrorCode.CONFLICT.name());
        pd.setDetail("Violação de integridade de dados.");
        pd.setProperty("code", ErrorCode.CONFLICT.name());
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", OffsetDateTime.now());
        // opcional: parsear mensagem para descobrir qual constraint (ex: cnpj único)
        pd.setProperty("rootCause", ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage());
        return ResponseEntity.status(409).body(pd);
    }

    // === Segurança ===
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleAuth(AuthenticationException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(401);
        pd.setTitle(ErrorCode.AUTHENTICATION_FAILED.name());
        pd.setDetail(ex.getMessage());
        pd.setProperty("code", ErrorCode.AUTHENTICATION_FAILED.name());
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", OffsetDateTime.now());
        return ResponseEntity.status(401).body(pd);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(403);
        pd.setTitle(ErrorCode.ACCESS_DENIED.name());
        pd.setDetail("Acesso negado.");
        pd.setProperty("code", ErrorCode.ACCESS_DENIED.name());
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", OffsetDateTime.now());
        return ResponseEntity.status(403).body(pd);
    }

    // === Fallback ===
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(500);
        pd.setTitle(ErrorCode.INTERNAL_ERROR.name());
        pd.setDetail("Ocorreu um erro inesperado.");
        pd.setProperty("code", ErrorCode.INTERNAL_ERROR.name());
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", OffsetDateTime.now());
        pd.setProperty("exception", ex.getClass().getSimpleName());
        return ResponseEntity.status(500).body(pd);
    }
}
