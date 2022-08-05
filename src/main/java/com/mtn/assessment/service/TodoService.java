package com.mtn.assessment.service;

import com.mtn.assessment.domain.Todo;
import com.mtn.assessment.exception.TodoNotFoundException;
import com.mtn.assessment.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public TodoService(TodoRepository todoRepository, RestTemplate restTemplate) {
        this.todoRepository = todoRepository;
        this.restTemplate = restTemplate;
    }

    @Cacheable("todos")
    public Todo getTodoById(Long id) {
        return todoRepository
                .findById(id)
                .orElseThrow(() -> new TodoNotFoundException(1l));
    }

    public void loadTodosData(String url) throws IOException {
        Stream todosStream = Arrays.stream(restTemplate.getForObject(url, Todo[].class));
        todosStream.forEach(todo -> createTodo((Todo)todo));
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public List<Todo> getAll() {
        return StreamSupport.stream(todoRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    public List<Todo> findByUserIdAndCompleted(Long userId,Boolean completed) {
        return StreamSupport.stream(todoRepository.findByUserIdAndCompleted(userId,completed).spliterator(), false).collect(Collectors.toList());
    }
}
