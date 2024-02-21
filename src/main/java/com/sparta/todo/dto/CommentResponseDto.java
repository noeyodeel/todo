package com.sparta.todo.dto;

import com.sparta.todo.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {

    private Long id;
    private String content;
    private LoginRequestDto user;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getComment_id();
        this.content = comment.getContent();
        this.user = new LoginRequestDto(comment.getUser().getUsername());
        this.createDate = LocalDateTime.now();
    }
}
