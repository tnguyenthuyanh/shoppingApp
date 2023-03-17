package com.thu.auth.AOP;


import com.thu.auth.domain.common.ErrorResponse;
import com.thu.auth.domain.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {InvalidCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }


}
