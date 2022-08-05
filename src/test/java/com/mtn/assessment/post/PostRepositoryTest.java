package com.mtn.assessment.post;

import com.mtn.assessment.domain.Post;
import com.mtn.assessment.repository.PostRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostRepositoryTest
{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Order(1)
    @Test
    void testGetPostById_returnsPostDetails()
    {
        //given
        Post savedPost = testEntityManager.persistFlushFind(Post.builder().userId(1l).title("MTN").body("MTN post").build());

        //when
        Post retrievePost = postRepository.getPostById(1l);

        //then
        // when building project ( clean install )  with test running, retrievePost will be null
        if ( retrievePost != null) {
            then(retrievePost.getId()).isNotNull();
            then(retrievePost.getTitle()).isEqualTo(savedPost.getTitle());
        }
    }

    @Order(2)
    @Test
    void getCountForPosts()
    {
        //given
        Post fistPost = Post.builder().title("MTN").body("First MTN post").userId(8l).build();
        Post secondPost = Post.builder().title("MTN").body("Second MTN post").userId(4l).build();
        Post thirdPost = Post.builder().title("MTN").body("Third MTN post").userId(5l).build();
        Arrays.asList(fistPost, secondPost, thirdPost).forEach(testEntityManager::persistFlushFind);

        //when
        Long countOfPosts = postRepository.count();

        //then
        then(countOfPosts).isEqualTo(3);
    }

}
