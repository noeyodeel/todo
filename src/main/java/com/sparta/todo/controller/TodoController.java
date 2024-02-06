package com.sparta.todo.controller;

import com.sparta.todo.dto.TodoListResponseDto;
import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.security.UserDetailsImpl;
import com.sparta.todo.service.TodoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo") //일정 생성
    public TodoResponseDto createTodo(@Valid @RequestBody TodoRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return todoService.createTodo(requestDto, userDetails);
    }

    @GetMapping("/todo/{id}") //선택한 todos 보기
    public TodoListResponseDto getIdTodos(@PathVariable Long id) {
        return todoService.getidTodos(id);
    }

    @GetMapping("/todo")//로그인한 사람이 쓴 글 전체 보기
    public List<TodoListResponseDto> getTodos(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.getTodos(userDetails.getUser());
    }

    @GetMapping("/todolist")
    public List<TodoListResponseDto> getAllTodos() {
        return todoService.getAlltodos();
    }

}
