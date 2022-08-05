package com.mtn.assessment.comment;

import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.repository.CommentRepository;
import com.mtn.assessment.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class CommentCacheTest
{

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    void getCommentById_forMultipleRequests_isRetrievedFromCache()
    {
        //given
        Long id = 123l;
        given(commentRepository.findById(id)).willReturn(Optional.of(Comment.builder().id(id).postId(id).name("Alireza").email("AlirezaGholamzadehLahrudi@gmail.com").body("Post body").build()));

        //when
        commentService.getCommentById(id);
        commentService.getCommentById(id);
        commentService.getCommentById(id);

        //then
        then(commentRepository).should(times(1)).findById(id);
    }
}
