package com.sparta.todo.controller;

import com.sparta.todo.dto.TodoResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<TodoResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        TodoResponseDto restApiException = new TodoResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
            restApiException,
            HttpStatus.BAD_REQUEST
        );
    }

}
