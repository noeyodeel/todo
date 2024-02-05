package com.sparta.todo.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class SignupResponseDto {

    private final String message;
    private final HttpStatus httpStatus;


}
