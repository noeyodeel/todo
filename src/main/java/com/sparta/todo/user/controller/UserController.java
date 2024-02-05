package com.sparta.todo.user.controller;


import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.dto.SignupResponseDto;
import com.sparta.todo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    @PostMapping("/user/signup")
    public ResponseEntity<SignupResponseDto> signup(
        @Valid @RequestBody SignupRequestDto requestDto) {

        userService.signup(requestDto);
        String message = requestDto.getUsername() + "님 회원가입이 완료되었습니다.";

        return ResponseEntity.ok().body(new SignupResponseDto(message, HttpStatus.OK));

    }

}
