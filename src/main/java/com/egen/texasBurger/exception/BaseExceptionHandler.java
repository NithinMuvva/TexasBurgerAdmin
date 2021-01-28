package com.egen.texasBurger.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.egen.texasBurger.exception.ResourceNotFoundException;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class BaseExceptionHandler {
	

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<?> handleNotFoundException(ResourceNotFoundException nfe, HttpServletRequest httpReq, HttpServletResponse httpRes) {
    	log.warn(nfe.getMessage());
        httpRes.setStatus(HttpStatus.NOT_FOUND.value()); // 404 (order or item not found)
        return new ResponseEntity(nfe.getMessage(),HttpStatus.NOT_FOUND);
    }
    
    @SuppressWarnings("unchecked")
	@ExceptionHandler({Exception.class, RuntimeException.class})
    private ResponseEntity<?> handleException(Throwable e, HttpServletRequest httpReq, HttpServletResponse httpRes) {
    	log.error(e.getMessage());
        httpRes.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
