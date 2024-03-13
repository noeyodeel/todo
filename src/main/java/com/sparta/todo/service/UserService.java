package com.sparta.todo.service;


import com.sparta.todo.dto.SignupRequestDto;

public interface UserService {

    void signup(SignupRequestDto requestDto);

}