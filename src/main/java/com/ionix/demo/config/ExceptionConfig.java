package com.ionix.demo.config;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice(annotations = RestController.class)
@RestControllerAdvice
public class ExceptionConfig {
        
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity <?> notFoundException (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

        @ExceptionHandler({BadRequestException.class,
                      org.springframework.http.converter.HttpMessageNotReadableException.class,
                      org.springframework.web.HttpRequestMethodNotSupportedException.class,
                      org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
                      java.lang.NumberFormatException.class,
                      org.springframework.web.client.HttpClientErrorException.class,
                      java.sql.SQLIntegrityConstraintViolationException.class
                      })
    public ResponseEntity <?> BadRequestException (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({org.springframework.dao.QueryTimeoutException.class, 
                       org.springframework.web.client.ResourceAccessException.class})
    public ResponseEntity<?> InternalException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body( "Ocurrio un error interno:" + e.getMessage());
    }
}
