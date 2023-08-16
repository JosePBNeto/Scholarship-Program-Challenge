package jose.patricio.ScolarshipChallenge.exceptions;

import org.flywaydb.core.api.ErrorDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    private CustomErrorDetails customErrorDetails;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomErrorDetails errorDetails = new CustomErrorDetails(LocalDateTime.now(),
                ex.getMostSpecificCause().getMessage(), request.getDescription(false));

        if (ex.getMessage().contains("error: Failed to parse Date")){
            errorDetails = new CustomErrorDetails(LocalDateTime.now(),
                    "Insert a valid date. FORMAT: yyyy-MM-dd format", request.getDescription(false));
        }

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomErrorDetails errorDetails = new CustomErrorDetails(LocalDateTime.now(),
                ex.getFieldError().getDefaultMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleIdNotFound(IdNotFoundException ex, WebRequest request) {
        customErrorDetails = new CustomErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(customErrorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClassArgumentException.class)
    public ResponseEntity<ErrorDetails> handleClassException (ClassArgumentException ex, WebRequest request) {
        customErrorDetails = new CustomErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(customErrorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<Object> handleInvalidEnumException(InvalidEnumValueException ex, WebRequest request) {
        customErrorDetails = new CustomErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDuplicatedUniqueData(DataIntegrityViolationException ex, WebRequest request) {
        CustomErrorDetails errorDetails = new CustomErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        if (ex.getMessage().contains("email")) {
            errorDetails = new CustomErrorDetails(LocalDateTime.now(), "Email already exists", request.getDescription(false));
        } else if (ex.getMessage().contains("number")){
            errorDetails = new CustomErrorDetails(LocalDateTime.now(), "Student number already exists", request.getDescription(false));
        } else if (ex.getMessage().contains("PRIMARY")) {
            errorDetails = new CustomErrorDetails(LocalDateTime.now(), "Duplicated ID", request.getDescription(false));
        }

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }




}
