package com.mtn.assessment.post;

import com.mtn.assessment.domain.Post;
import com.mtn.assessment.exception.PostNotFoundException;
import com.mtn.assessment.repository.PostRepository;
import com.mtn.assessment.service.PostService;
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
public class PostServiceTest
{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @DisplayName("Returning saved post from service layer")
    @Test
    void getPostById_forSavedPost_isReturned()
    {
        //given
        Post savedPost = postRepository.save(Post.builder().title("Post title").body("Post body").userId(4l).build());

        //when
        Post retrievedPost = postService.getPostById(savedPost.getId());

        //then
        then(retrievedPost.getTitle()).isEqualTo("Post title");
        then(retrievedPost.getId()).isNotNull();
    }

    @Test
    void getPostById_whenMissingPost_notFoundExceptionThrown()
    {
        //given
        Long id = 1234l;
        //when
        Throwable throwable = catchThrowable(() -> postService.getPostById(id));

        //then
        BDDAssertions.then(throwable).isInstanceOf(PostNotFoundException.class);
    }

}



