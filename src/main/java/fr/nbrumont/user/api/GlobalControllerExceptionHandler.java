package fr.nbrumont.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * Creates a global API error message when we explicitly throw a {@link ResponseStatusException}
     * The response will have correct status and reason without any other field
     *
     * @param ex {@link ResponseStatusException} the exception to handle
     * @return a {@link ResponseEntity} containing a message
     */
    @ExceptionHandler(ResponseStatusException.class)
    private ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(errors);
    }

    /**
     * Creates a global API error message when a validation fails ({@link MethodArgumentNotValidException})
     * The response will have a 400 statut and a json object containing each failed field as a key
     *
     * @param ex {@link MethodArgumentNotValidException} the exception to handle
     * @return a {@link ResponseEntity} containing a map { field => message }
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
