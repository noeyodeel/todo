package com.sparta.todo.service;

import com.sparta.todo.dto.TodoListResponseDto;
import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import java.util.List;

public interface Todoservice {

    TodoResponseDto createTodo(TodoRequestDto requestDto,
        User userDetails);
    TodoResponseDto getidTodos(Long id, User userDetails);
    List<TodoListResponseDto> getTodos(User user);
    List<TodoListResponseDto> getAlltodos();
    TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, User userDetails);
    TodoResponseDto completeTodo(Long id, User userDetails);
    Todo getTodo(Long postId);

}
