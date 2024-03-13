package com.sparta.todo.controller;

import com.sparta.todo.dto.ApiResponseDto;
import com.sparta.todo.dto.TodoResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResponseDto> handleIllegalArgumentException(
        IllegalArgumentException ex) {
        ApiResponseDto restApiException = new ApiResponseDto(ex.getMessage());
        return new ResponseEntity<>(
            restApiException,
            HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ApiResponseDto> handleNullPointerException(
        final NullPointerException ex) {
        ApiResponseDto restApiException = new ApiResponseDto(ex.getMessage());
        return new ResponseEntity<>(
            restApiException,
            HttpStatus.BAD_REQUEST
        );
    }

}
