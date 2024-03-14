package com.sparta.todo.controller;

import com.sparta.todo.dto.TodoListResponseDto;
import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.security.UserDetailsImpl;
import com.sparta.todo.service.TodoServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoServiceImpl todoService;

    @PostMapping() //일정 생성
    public TodoResponseDto createTodo(@Valid @RequestBody TodoRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return todoService.createTodo(requestDto, userDetails.getUser());
    }

    @GetMapping("/{id}") //선택한 게시글 조회
    public TodoResponseDto getIdTodos(@PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.getidTodos(id, userDetails.getUser());
    }

    @GetMapping()//로그인한 사람이 쓴 글 전체 조회
    public List<TodoListResponseDto> getTodos(
        @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("page") int page,
        @RequestParam("size") int size) {
        return todoService.getTodos(userDetails.getUser().getUsername(),page,size);
    }

    @GetMapping("/all") //전체 게시글 조회
    public List<TodoListResponseDto> getAllTodos() {
        return todoService.getAlltodos();
    }

    @PatchMapping("update/{id}") //선택한 게시글 수정
    public TodoResponseDto updateTodo(@PathVariable Long id,
        @Valid @RequestBody TodoRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.updateTodo(id, requestDto, userDetails.getUser());
    }

    @PatchMapping("/{id}/complete") //완료된 게시글 조회
    public TodoResponseDto completeTodo(@PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.completeTodo(id, userDetails.getUser());
    }


}
