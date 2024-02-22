package com.sparta.todo.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.TodoRepository;
import com.sparta.todo.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // @Mock 사용을 위해 설정합니다.
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;
    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("할일 생성 test")
    void test1(){
        //Given
        String title = "새 제목";
        String content = "새 할일 내용";
        TodoRequestDto todoRequestDto = new TodoRequestDto(title,content);
        User user = new User("doyeon12", "aaa123");

        Todo todo = new Todo(title, content, user);
        todoRepository.save(todo);
        TodoService todoService = new TodoService(userRepository,todoRepository);
        given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        //when
        TodoResponseDto result =  todoService.createTodo(todoRequestDto,user);

        // then
        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(content, result.getContent());
    }
    @Test
    @DisplayName("할일 선택 조회 test")
    void test2(){
        //Given
        Long id = 1L;
        User user = new User("doyeon12", "aaa123");
        Todo todo = new Todo();
        TodoService todoService = new TodoService(userRepository,todoRepository);
        given(todoRepository.findById(id)).willReturn(Optional.of(todo));

        //when
        TodoResponseDto result = todoService.getidTodos(id,user);

        // then
        assertNotNull(result);
    }
    @Test
    @DisplayName("todo 업데이트 test")
    void test3(){
        //Given
        Long id = 1L;
        String title = "수정된 제목";
        String content = "수정된 내용";
        TodoRequestDto todoRequestDto = new TodoRequestDto(title,content);
        User user = new User("doyeon12", "aaa123");

        Todo todo = new Todo();
        TodoService todoService = new TodoService(userRepository,todoRepository);
        given(todoRepository.findById(id)).willReturn(Optional.of(todo));

        //when
        TodoResponseDto result =  todoService.updateTodo(id, todoRequestDto,user);

        // then
        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(content, result.getContent());
    }
    @Test
    @DisplayName("todo 업데이트 test")
    void test4() {
        //Given
        Long id = 1L;
        User user = new User("doyeon12", "aaa123");
        Todo todo = new Todo();

        TodoService todoService = new TodoService(userRepository,todoRepository);
        given(todoRepository.findById(id)).willReturn(Optional.of(todo));
        given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        //when
        TodoResponseDto result = todoService.completeTodo(id,user);
        // then
        assertNotNull(result);
        assertEquals(true, result.isCompleted());
    }
}
