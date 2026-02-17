package com.banking.exception;


import com.banking.dto.error.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError>
    handleMethodArgumentNotValidException(MethodArgumentNotValidException
                                                  methodArgumentNotValidException
            , HttpServletRequest request){

        Map<String, String> errors = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(fe ->
                errors.put(fe.getField(), fe.getDefaultMessage())
        );

        methodArgumentNotValidException.getBindingResult().getGlobalErrors().forEach(ge ->
                errors.put("Balance", ge.getDefaultMessage())
        );


        return ResponseEntity.status(400).body(ApiError.builder()
                .message("Validation Exception")
                .path(request.getRequestURI())
                .validationError(errors.isEmpty()?null:errors)
                .timeStamp(new Date().getTime())
                .build());

    }




    @ExceptionHandler(CustomerNotFound.class)
    public ResponseEntity<ApiError> handleCustomerNotFoundException(CustomerNotFound
                                                                            exception
            , HttpServletRequest request){
        return ResponseEntity.status(404).body(
                ApiError.builder()
                        .timeStamp(new Date().getTime())
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(TransactionNotFound.class)
    public ResponseEntity<ApiError> handlePurchaseTransactionNotFound(TransactionNotFound
                                                                              exception
            , HttpServletRequest request){
        return ResponseEntity.status(404).body(
                ApiError.builder()
                        .timeStamp(new Date().getTime())
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleCustomerNotFoundException(Exception
                                                                            exception
            , HttpServletRequest request){
        exception.printStackTrace();
        return ResponseEntity.status(500).body(
                ApiError.builder()
                        .timeStamp(new Date().getTime())
                        .message("Unexpected error")
                        .path(request.getRequestURI())
                        .build()
        );
    }

}
