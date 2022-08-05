package com.mtn.assessment.post;

import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.domain.Post;
import com.mtn.assessment.domain.Todo;
import com.mtn.assessment.service.CommentService;
import com.mtn.assessment.service.PostService;
import com.mtn.assessment.service.TodoService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@WebMvcTest
public class PostControllerBaseClass
{
    @MockBean
    private PostService postService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup()
    {
        RestAssuredMockMvc.mockMvc(mockMvc);

        //given
        given(postService.getPostById(anyLong())).willReturn(
                Post.builder()
                       .id(anyLong())
                       .title("Post title")
                       .userId(anyLong())
                       .build()
        );


    }
}
