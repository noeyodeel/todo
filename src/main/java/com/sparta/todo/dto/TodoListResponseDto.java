package com.sparta.todo.dto;

import com.sparta.todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoListResponseDto {

    private String title;
    private String user;
    private LocalDateTime createAt;
    private boolean isCompleted;


    public TodoListResponseDto(Todo todo) {
        this.title = todo.getTitle();
        this.user = todo.getUser().getUsername();
        this.createAt = todo.getCreatAt();
        this.isCompleted = todo.isCompleted();
    }


}
