package com.mtn.assessment.comment;

import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class CommentRepositoryTest
{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testGetCommentById_returnsCommentDetails()
    {
        //given
        Comment savedComment = testEntityManager.persistFlushFind(Comment.builder().name("MTN").body("MTN comment").build());

        //when
        Comment retrieveComment = commentRepository.getReferenceById(1l);

        //then
        then(retrieveComment.getId()).isNotNull();
        then(retrieveComment.getName()).isEqualTo(savedComment.getName());
    }

    @Test
    void getCountForComment()
    {
        //given
        Comment fistComment = Comment.builder().name("MTN").body("First MTN comment").postId(8l).build();
        Comment secondComment = Comment.builder().name("MTN").body("Second MTN comment").postId(4l).build();
        Comment thirdComment = Comment.builder().name("MTN").body("Third MTN comment").postId(5l).build();
        Arrays.asList(fistComment, secondComment, thirdComment).forEach(testEntityManager::persistFlushFind);

        //when
        Long counts = commentRepository.count();

        //then
        then(counts).isEqualTo(3);
    }

}
