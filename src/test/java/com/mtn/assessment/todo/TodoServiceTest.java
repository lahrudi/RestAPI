package com.mtn.assessment.todo;

import com.mtn.assessment.controller.CommentController;
import com.mtn.assessment.controller.PostController;
import com.mtn.assessment.domain.Post;
import com.mtn.assessment.domain.Todo;
import com.mtn.assessment.exception.PostNotFoundException;
import com.mtn.assessment.exception.TodoNotFoundException;
import com.mtn.assessment.repository.TodoRepository;
import com.mtn.assessment.service.LoadDataService;
import com.mtn.assessment.service.TodoService;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.transaction.Transactional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@Transactional
public class TodoServiceTest
{

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @DisplayName("Returning saved todo from service layer")
    @Test
    void getTodoById_forSavedTodo_isReturned()
    {
        //given
        Todo savedTodo = todoRepository.save(Todo.builder().title("todo").completed(true).userId(4l).build());

        //when
        Todo retrievedPost = todoService.getTodoById(savedTodo.getId());

        //then
        then(retrievedPost.getTitle()).isEqualTo("todo");
        then(retrievedPost.getId()).isNotNull();
    }

    @Test
    void getTodoById_whenMissingTodo_notFoundExceptionThrown()
    {
        //given
        Long id = 1234l;
        //when
        Throwable throwable = catchThrowable(() -> todoService.getTodoById(id));

        //then
        BDDAssertions.then(throwable).isInstanceOf(TodoNotFoundException.class);
    }

}



