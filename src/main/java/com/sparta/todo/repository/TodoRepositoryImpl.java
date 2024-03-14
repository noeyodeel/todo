package com.sparta.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.todo.entity.QTodo;
import com.sparta.todo.entity.Todo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Todo> findAllByUserOrderByCreatAtDesc(String username) {
        QTodo todo = QTodo.todo;
        return jpaQueryFactory.selectFrom(todo)
            .where(todo.user.username.eq(username)) // 해당 사용자의 Todo만 선택
            .orderBy(todo.creatAt.desc()) // 생성 날짜의 내림차순으로 정렬
            .fetch();
    }
}
