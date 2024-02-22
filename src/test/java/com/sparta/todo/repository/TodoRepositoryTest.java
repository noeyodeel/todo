package com.sparta.todo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = new User("doyeon1", "aaa123");
        userRepository.save(user); // User 엔티티를 저장

        String title1 = "새 제목1";
        String content1 = "새 할일 내용1";
        String title2 = "새 제목2";
        String content2 = "새 할일 내용2";

        Todo todo1 = new Todo(title1, content1, user);
        Todo todo2 = new Todo(title2, content2, user);

        todoRepository.saveAll(List.of(todo1, todo2));
    }

    @Test
    @DisplayName("특정 사용자의 할일을 시간 내림차순으로 조회")
    public void findAllByUserOrderByCreatAtDesc() {
        // Given
        User user = userRepository.findByUsername("doyeon1").orElseThrow(() ->
            new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        // When
        List<Todo> todos = todoRepository.findAllByUserOrderByCreatAtDesc(user);

        // Then
        assertEquals(2, todos.size()); // 예상한 결과 크기가 맞는지 확인
        assertEquals("새 제목1", todos.get(0).getTitle());
    }
}


