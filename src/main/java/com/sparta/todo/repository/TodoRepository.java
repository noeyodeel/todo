package com.sparta.todo.repository;

import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom{

}
