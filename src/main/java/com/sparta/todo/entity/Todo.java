package com.sparta.todo.entity;

import com.sparta.todo.dto.TodoRequestDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @Pattern(regexp = "^[가-힣]*$", message = "한글만 입력 가능합니다.")
    private String title;


    @Column(nullable = false, length = 1000)
    @Pattern(regexp = "^[가-힣]*$", message = "한글만 입력 가능합니다.")
    private String content;

    @Column(updatable = false)
    private LocalDateTime creatAt;

    @Column(nullable = false)
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Todo(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.creatAt = LocalDateTime.now();
        this.user = user;
    }

    public void update(TodoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void complete() {
        this.isCompleted = true;
    }
}
