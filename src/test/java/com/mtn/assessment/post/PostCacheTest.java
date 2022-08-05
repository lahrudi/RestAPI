package com.mtn.assessment.post;

import com.mtn.assessment.domain.Post;
import com.mtn.assessment.repository.PostRepository;
import com.mtn.assessment.service.PostService;
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
public class PostCacheTest
{

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Test
    void getPostById_forMultipleRequests_isRetrievedFromCache()
    {
        //given
        Long id = 123l;
        given(postRepository.findById(id)).willReturn(Optional.of(Post.builder().id(1l).title("Post title").body("Post body").build()));

        //when
        postService.getPostById(id);
        postService.getPostById(id);
        postService.getPostById(id);

        //then
        then(postRepository).should(times(1)).findById(id);
    }
}
