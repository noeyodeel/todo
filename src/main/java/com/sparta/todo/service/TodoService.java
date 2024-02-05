package com.sparta.todo.service;

import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.TodoRepository;
import com.sparta.todo.repository.UserRepository;
import com.sparta.todo.security.UserDetailsImpl;
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
}
