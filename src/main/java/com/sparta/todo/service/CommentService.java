package com.sparta.todo.service;

import com.sparta.todo.dto.CommentRequestDto;
import com.sparta.todo.dto.CommentResponseDto;
import com.sparta.todo.entity.User;


public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto requestDto, User user);

    CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto,
        User user);

    void deleteComment(Long commentId, User user);

}
