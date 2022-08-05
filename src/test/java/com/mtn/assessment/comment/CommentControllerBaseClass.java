package com.mtn.assessment.comment;

import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.service.CommentService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@WebMvcTest
public class CommentControllerBaseClass
{
    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup()
    {
        RestAssuredMockMvc.mockMvc(mockMvc);

        //given
        given(commentService.getCommentById(anyLong())).willReturn(
                Comment.builder()
                       .id(anyLong())
                       .name("Mike")
                       .postId(anyLong())
                       .build()
        );
    }
}
