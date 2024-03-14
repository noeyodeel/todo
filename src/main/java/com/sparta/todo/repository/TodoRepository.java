package com.sparta.todo.repository;

import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@RepositoryDefinition(domainClass = Todo.class, idClass = Long.class)
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom{

}
