package com.mtn.assessment.todo;

import com.mtn.assessment.domain.Todo;
import com.mtn.assessment.service.TodoService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@WebMvcTest
public class TodoControllerBaseClass
{
    @MockBean
    private TodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup()
    {
        RestAssuredMockMvc.mockMvc(mockMvc);

        //given
        given(todoService.getTodoById(anyLong())).willReturn(
                Todo.builder()
                       .id(anyLong())
                       .completed(true)
                       .title("title")
                       .userId(anyLong())
                       .build()
        );
    }
}
