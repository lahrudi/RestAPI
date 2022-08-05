package com.mtn.assessment.comment;

import com.mtn.assessment.controller.PostController;
import com.mtn.assessment.controller.TodoController;
import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.exception.CommentNotFoundException;
import com.mtn.assessment.service.CommentService;
import com.mtn.assessment.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CommentControllerTest {
    @MockBean
    private CommentService commentService;

    @MockBean
    private PostController postController;

    @MockBean
    private TodoController todoController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getComment_ForSavedComment_Returned() throws Exception {
        //given
        doReturn(
                Comment.builder()
                        .id(1l)
                        .name("Alireza")
                        .postId(4l)
                        .email("email address")
                        .body("body")
                        .build()).when(commentService).getCommentById(anyLong());

        //when then
        mockMvc.perform(get("/comments/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1l))
                .andExpect(jsonPath("name").value("Alireza"))
                .andExpect(jsonPath("postId").value(4l));
    }

    @Test
    void getComment_ForMissingComment_status404() throws Exception {
        //given
        given(commentService.getCommentById(anyLong())).willThrow(CommentNotFoundException.class);

        //when then
        mockMvc.perform(get("/comments/1"))
                .andExpect(status().isNotFound());

    }

}
