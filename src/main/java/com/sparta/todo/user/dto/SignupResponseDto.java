package com.sparta.todo.user.dto;

import com.sparta.todo.user.entity.User;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private Long id;
    private String userName;


    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
    }


    public SignupResponseDto(String token, String username) {
    }
}
