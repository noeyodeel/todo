package com.sparta.todo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EntityTest {

//    Given : 어떠한 데이터가 주어질 때.
//    When : 어떠한 기능을 실행하면.
//    Then : 어떠한 결과를 기대한다.

    Todo todo;
    User user;
    @BeforeEach
    void setUp() {
        user = new User();
        todo = new Todo();

    }

    @Test
    @DisplayName("Todo 수정 test")
    void test1() {
        // Given
        String title = "바뀐 제목";
        String content = "바뀐 내용";
        // When
        TodoRequestDto requestDto = new TodoRequestDto(title,content);
        todo.update(requestDto);
        // Then
        assertEquals(title, todo.getTitle());
        assertEquals(content, todo.getContent());
    }
    @Test
    @DisplayName("완료된 todo test")
    void test2(){
        // Given
        todo.complete();
        // When - Then
        assertEquals(true,todo.isCompleted());
    }
    // Given
    // When
    // Then

}
