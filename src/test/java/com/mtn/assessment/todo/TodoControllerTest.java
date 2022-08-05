package com.mtn.assessment.todo;

import com.mtn.assessment.controller.CommentController;
import com.mtn.assessment.domain.Todo;
import com.mtn.assessment.exception.TodoNotFoundException;
import com.mtn.assessment.service.CommentService;
import com.mtn.assessment.service.PostService;
import com.mtn.assessment.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TodoControllerTest {
    @MockBean
    private TodoService todoService;

    @MockBean
    private PostService postService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentController commentController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTodo_ForSavedTodo_Returned() throws Exception {
        //given
        List<Todo> todoList = new ArrayList<>();
        todoList.add(Todo.builder()
                .id(1l)
                .completed(true)
                .userId(4l)
                .title("title")
                .build());
        doReturn(
                todoList).when(todoService).findByUserIdAndCompleted(anyLong(),anyBoolean());

        //when then
        mockMvc.perform(get("/todos?userId=1&completed=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1l))
                .andExpect(jsonPath("$[0].userId").value(4l))
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].completed").value(true));
    }

    @Test
    void getTodo_ForMissingTodo_status404() throws Exception {
        //given
        given(todoService.findByUserIdAndCompleted(anyLong(),anyBoolean())).willThrow(TodoNotFoundException.class);

        //when then
        mockMvc.perform(get("/todos?userId=1&completed=true"))
                .andExpect(status().isNotFound());

    }

}
