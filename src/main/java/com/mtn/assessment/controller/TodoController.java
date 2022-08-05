package com.mtn.assessment.controller;

import com.mtn.assessment.commons.TodoWrapper;
import com.mtn.assessment.domain.Todo;
import com.mtn.assessment.dto.TodoDto;
import com.mtn.assessment.exception.TodoNotFoundException;
import com.mtn.assessment.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Value("${error.message}")
    private String errorMessage;

    @GetMapping()
    public ResponseEntity<List<TodoDto>> getTodos() {
        List<Todo> todos = todoService.getAll();
        List<TodoDto> todoDtoList = todos.stream().map(TodoDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(todoDtoList, HttpStatus.OK);
    }

    @GetMapping(params = {"userId","completed"})
    public ResponseEntity<List<TodoDto>> getCommentsByPostId(
            @RequestParam(name = "userId",required = true) Long userId,
            @RequestParam(name = "completed",required = true) Boolean completed  ) {
        final List<Todo> todoList = todoService.findByUserIdAndCompleted(userId,completed);
        List<TodoDto> todoDtoList = todoList.stream().map(todo -> TodoWrapper.mapToDTO(todo)).collect(Collectors.toList());
        return new ResponseEntity<>(todoDtoList, HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void todoNotFoundHandler(TodoNotFoundException e) {
    }
}