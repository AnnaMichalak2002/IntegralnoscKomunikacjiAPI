package edu.zut.pbalab4.exception;

import edu.zut.pbalab4.model.Error;
import edu.zut.pbalab4.model.ResponseHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleUserNotFound(UserNotFoundException ex) {
        Error error = new Error();
        error.setResponseHeader(buildResponseHeader());
        error.setCode("NOT_FOUND");
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Error> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        Error error = new Error();
        error.setResponseHeader(buildResponseHeader());
        error.setCode("USER_ALREADY_EXISTS");
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(error);
    }

    @ExceptionHandler(InvalidSignatureException.class)
    public ResponseEntity<Error> handleInvalidSignature(InvalidSignatureException ex) {
        Error error = new Error();
        error.setResponseHeader(buildResponseHeader());
        error.setCode("INVALID_SIGNATURE");
        error.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(error);
    }

    @ExceptionHandler(InvalidJwsSignatureException.class)
    public ResponseEntity<Error> handleInvalidJwsSignature(InvalidJwsSignatureException ex) {
        Error error = new Error();
        error.setResponseHeader(buildResponseHeader());
        error.setCode("INVALID_JWS_SIGNATURE");
        error.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Validation error");

        Error error = new Error();
        error.setResponseHeader(buildResponseHeader());
        error.setCode("BAD_REQUEST");
        error.setMessage(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleOther(Exception ex) {
        Error error = new Error();
        error.setResponseHeader(buildResponseHeader());
        error.setCode("INTERNAL_ERROR");
        error.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private ResponseHeader buildResponseHeader() {
        ResponseHeader header = new ResponseHeader();
        header.setRequestId(UUID.randomUUID());
        header.setSendDate(OffsetDateTime.now());
        return header;
    }
}