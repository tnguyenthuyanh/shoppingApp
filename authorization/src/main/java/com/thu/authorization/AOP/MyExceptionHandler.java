package com.thu.authorization.AOP;


import com.thu.authorization.domain.response.ErrorResponse;
import com.thu.authorization.domain.exception.NotEnoughInventoryException;
import com.thu.authorization.domain.exception.NotEnoughInventoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {NotEnoughInventoryException.class})
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(NotEnoughInventoryException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }


}
