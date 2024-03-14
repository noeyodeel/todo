package com.sparta.todo.repository;

import com.sparta.todo.entity.Todo;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepositoryCustom {
    List<Todo> findAllByUserOrderByCreatAtDesc(String username);

}
