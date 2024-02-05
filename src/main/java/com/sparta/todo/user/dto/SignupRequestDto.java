package com.sparta.todo.user.dto;


import com.sparta.todo.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Size(min = 4, max = 10, message = "아이디는 최소 4자 이상, 10자 이하로 입력하세요.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.")
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상, 15자 이하로 입력하세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).*$", message = "비밀번호는 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.")
    private String password;


}