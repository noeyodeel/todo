package com.sparta.todo.dto;


import com.sparta.todo.entity.Todo;
import java.time.LocalDateTime;
import javax.swing.plaf.PanelUI;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

@Setter
@Getter
public class TodoResponseDto {
    private String title;
    private String content;
    private String user;
    private LocalDateTime createAt;
    private boolean isCompleted;

    public TodoResponseDto(String title, String content, String user, LocalDateTime createAt,
        boolean isCompleted) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.createAt = createAt;
        this.isCompleted = isCompleted;
    }

    public TodoResponseDto(Todo todo, String username) {
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.user = username;
        this.createAt = todo.getCreatAt();
        this.isCompleted = todo.isCompleted();

    }
}
