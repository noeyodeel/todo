package com.sparta.todo.dto;

import com.sparta.todo.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;

    public LoginRequestDto(User user) {
        this.username = user.getUsername();
    }

    public LoginRequestDto(String username) {
        this.username = username;
    }
}
