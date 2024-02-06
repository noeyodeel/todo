package com.sparta.todo.service;

import com.sparta.todo.dto.TodoListResponseDto;
import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.TodoRepository;
import com.sparta.todo.repository.UserRepository;
import com.sparta.todo.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto,
        UserDetailsImpl userDetails) {
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(() ->
            new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );

        Todo todo = new Todo(title, content, user);
        todoRepository.save(todo);

        return new TodoResponseDto(todo, username);
    }

    public TodoResponseDto getidTodos(Long id, UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();

        Todo todo = todoRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
        return new TodoResponseDto(todo, username);

    }

    public List<TodoListResponseDto> getTodos(User user) {

        return todoRepository.findAllByUserOrderByCreatAtDesc(user).stream()
            .map(TodoListResponseDto::new).toList();

    }

    public List<TodoListResponseDto> getAlltodos() {
        List<Todo> todoList = todoRepository.findAll();
        return todoList.stream().map(todo -> new TodoListResponseDto(todo)).toList();

    }

    @Transactional
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, UserDetailsImpl userDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
        String username = userDetails.getUsername();
        todo.update(requestDto);
        return  new TodoResponseDto(todo,username);
    }
    @Transactional
    public TodoResponseDto completeTodo(Long id, UserDetailsImpl userDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->
            new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        todo.complete();
        return new  TodoResponseDto(todo,user.getUsername());
    }
}
