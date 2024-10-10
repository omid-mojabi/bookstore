package bookstore.controller;

import bookstore.exception.ItemInsufficientException;
import bookstore.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(ConstraintViolationException exception){
        final Map<String, String> errorMap= new HashMap<>();
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            errorMap.put( constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
        }
        log.debug(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(ItemInsufficientException.class)
    public ResponseEntity<Map<String,String>> handleInsufficientException(ItemInsufficientException exception){
        final Map<String, String> errorMap= new HashMap<>();
        errorMap.put("Book has insufficient balance:",exception.getMessage());
        log.debug(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleResourceNotFoundException(ResourceNotFoundException exception){
        final Map<String, String> errorMap= new HashMap<>();
        errorMap.put("message",exception.getMessage());
        log.debug(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleSqlException(DataIntegrityViolationException exception){
        final Map<String, String> errorMap= new HashMap<>();
        errorMap.put("message","Error was occurred in database operation");
        log.debug(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
}