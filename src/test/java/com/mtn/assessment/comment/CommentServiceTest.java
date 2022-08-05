package com.mtn.assessment.comment;

import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.exception.CommentNotFoundException;
import com.mtn.assessment.repository.CommentRepository;
import com.mtn.assessment.service.CommentService;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@Transactional
public class CommentServiceTest
{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @DisplayName("Returning saved comment from service layer")
    @Test
    void getCommentById_forSavedComment_isReturned()
    {
        //given
        Comment savedComment = commentRepository.save(Comment.builder().name("Comment").body("Comment body").postId(4l).build());

        //when
        Comment retrievedComment = commentService.getCommentById(savedComment.getId());

        //then
        then(retrievedComment.getName()).isEqualTo("Comment");
        then(retrievedComment.getId()).isNotNull();
    }

    @Test
    void getCommentById_whenMissingComment_notFoundExceptionThrown()
    {
        //given
        Long id = 1234l;
        //when
        Throwable throwable = catchThrowable(() -> commentService.getCommentById(id));

        //then
        BDDAssertions.then(throwable).isInstanceOf(CommentNotFoundException.class);
    }

}



