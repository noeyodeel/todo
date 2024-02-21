package com.sparta.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
public class TodoRequestDto {

    private String title;
    private String content;


}
