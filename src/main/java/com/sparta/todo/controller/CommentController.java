package com.sparta.todo.controller;


import static com.sparta.todo.dto.StatusCheck.success;

import com.sparta.todo.dto.CommentRequestDto;
import com.sparta.todo.dto.CommentResponseDto;
import com.sparta.todo.dto.CommonResponse;
import com.sparta.todo.security.UserDetailsImpl;
import com.sparta.todo.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("")
    public ResponseEntity<CommonResponse<?>> createComment(
        @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {

        CommentResponseDto responseDto = commentService.createComment(requestDto,
            userDetails.getUser());

       return success("댓글이 성공적으로 생성되었습니다.", responseDto);
    }

    @PatchMapping("/{commentId}")
    public CommonResponse<CommentResponseDto> updateComment(@PathVariable Long commentId,
        @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {

        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto,
            userDetails.getUser());

        CommonResponse<CommentResponseDto> response = CommonResponse.<CommentResponseDto>builder()
            .statusCode(200)
            .msg("댓글이 성공적으로 수정되었습니다")
            .data(responseDto)
            .build();

        return response;
    }

    @DeleteMapping("/{commentId}")
    public CommonResponse<CommentResponseDto> deleteComment(
        @PathVariable Long commentId, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        CommonResponse<CommentResponseDto> response = CommonResponse.<CommentResponseDto>builder()
            .statusCode(200)
            .msg("댓글이 성공적으로 삭제되었습니다")
            .build();

        return response;

    }


}
