package com.mtn.assessment.post;

import com.mtn.assessment.controller.CommentController;
import com.mtn.assessment.controller.TodoController;
import com.mtn.assessment.domain.Post;
import com.mtn.assessment.exception.PostNotFoundException;
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
public class PostControllerTest {
    @MockBean
    private PostService postService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private TodoController todoController;

    @MockBean
    private CommentController commentController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPost_ForSavedPost_Returned() throws Exception {
        //given
        doReturn(
                Post.builder()
                        .id(1l)
                        .title("Post title")
                        .userId(4l)
                        .build()).when(postService).getPostById(anyLong());

        //when then
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1l))
                .andExpect(jsonPath("title").value("Post title"))
                .andExpect(jsonPath("userId").value(4l));
    }

    @Test
    void getPost_ForMissingPost_status404() throws Exception {
        //given
        given(postService.getPostById(anyLong())).willThrow(PostNotFoundException.class);

        //when then
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isNotFound());

    }

}
