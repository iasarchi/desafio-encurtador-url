package com.iasarchi.encurtadorurl.controller;

import com.iasarchi.encurtadorurl.exception.AlreadyExistsException;
import com.iasarchi.encurtadorurl.exception.InvalidURLException;
import com.iasarchi.encurtadorurl.exception.MessageError;
import com.iasarchi.encurtadorurl.exception.UrlNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShortenerControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    public MessageError handleAlreadyExistsException(AlreadyExistsException ex) {
        MessageError messageError = new MessageError("001", ex.getMessage());
        return messageError;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UrlNotFound.class)
    public MessageError handleUrlNotFoundException(UrlNotFound ex){
        MessageError messageError = new MessageError("002",ex.getMessage());
        return messageError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidURLException.class)
    public MessageError handleInvalidURLException(InvalidURLException ex){
        MessageError messageError = new MessageError("003",ex.getMessage());
        return messageError;
    }

}
