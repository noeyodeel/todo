package com.sparta.todo.service;

import com.sparta.todo.dto.TodoListResponseDto;
import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.TodoRepository;
import com.sparta.todo.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements Todoservice {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Override
    public TodoResponseDto createTodo(TodoRequestDto requestDto,
        User userDetails) {
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

    public TodoResponseDto getidTodos(Long id, User userDetails) {
        String username = userDetails.getUsername();

        Todo todo = todoRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
        return new TodoResponseDto(todo, username);

    }


    public List<TodoListResponseDto> getTodos(String username,int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return todoRepository.findAllByUserOrderByCreatAtDesc(username, pageRequest.getOffset(),pageRequest.getPageSize()).stream()
            .map(TodoListResponseDto::new).toList();

    }

    public List<TodoListResponseDto> getAlltodos() {
        List<Todo> todoList = todoRepository.findAll();
        return todoList.stream().map(todo -> new TodoListResponseDto(todo)).toList();

    }

    @Transactional
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, User userDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
        String username = userDetails.getUsername();
        todo.update(requestDto);
        return  new TodoResponseDto(todo,username);
    }
    @Transactional
    public TodoResponseDto completeTodo(Long id, User userDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->
            new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        todo.complete();
        return new  TodoResponseDto(todo,user.getUsername());
    }

    public Todo getTodo(Long postId) {
        return todoRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 id 입니다."));

    }
}
