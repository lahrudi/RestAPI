package com.mtn.assessment.service;

import com.mtn.assessment.domain.Post;
import com.mtn.assessment.dto.PostDto;
import com.mtn.assessment.exception.PostNotFoundException;
import com.mtn.assessment.payload.PostResponse;
import com.mtn.assessment.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.mtn.assessment.commons.PostWrapper.mapToDTO;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PostService(PostRepository postRepository, RestTemplate restTemplate) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
    }

    public void loadPostsData(String url) throws IOException {
        Stream postStream = Arrays.stream(restTemplate.getForObject(url, Post[].class));
        postStream.forEach(post -> createPost((Post) post));
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAll() {
        return StreamSupport.stream(postRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public PostResponse findPaginated(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("findPaginated method of PostService class calls with pageNumber : {} and pageSize : {}", pageNumber, pageSize);

        Sort sort = null;
        Pageable pageRequest = null;

        if (sortBy != null && !sortBy.isEmpty()) {
            sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
        }
        if (sort != null)
            pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        else
            pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<Post> posts = postRepository.findAll(pageRequest);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Cacheable("posts")
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    public Post update(Post post) {
        return postRepository.save(post);
    }

    public Post deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
        return post;
    }

    public List<Post> filter(String title) {
        return postRepository.filter("%" + title + "%");
    }
}