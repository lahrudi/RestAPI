package com.mtn.assessment.controller;

import com.mtn.assessment.commons.CommentWrapper;
import com.mtn.assessment.commons.PostWrapper;
import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.domain.Post;
import com.mtn.assessment.dto.CommentDto;
import com.mtn.assessment.dto.PostDto;
import com.mtn.assessment.exception.PostNotFoundException;
import com.mtn.assessment.payload.PostResponse;
import com.mtn.assessment.service.CommentService;
import com.mtn.assessment.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Value("${error.message}")
    private String errorMessage;

    @GetMapping()
    public ResponseEntity<List<PostDto>> getPosts() {
        List<Post> posts = postService.getAll();
        List<PostDto> postsDto = posts.stream().map(PostDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }

    @GetMapping(params = {"pageNo", "pageSize", "sortBy", "sortDir"})
    public ResponseEntity<PostResponse> findPaginated(@RequestParam(name = "pageNo",value = "pageNo") int pageNo,
                                                      @RequestParam(name = "pageSize",value = "pageSize") int pageSize,
                                                      @RequestParam(name = "sortBy",value = "sortBy",required = false) String sortBy,
                                                      @RequestParam(name = "sortDir",value = "sortDir",required = false) String sortDir) {
        PostResponse posts = postService.findPaginated(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        Post post = postService.getPostById(id);
        return new ResponseEntity<>(PostWrapper.mapToDTO(post), HttpStatus.OK);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("id") Long id) {
        final List<Comment> comments = commentService.getCommentByPostId(id);
        List<CommentDto> commentDtoList = comments.stream().map(comment -> CommentWrapper.mapToDTO(comment)).collect(Collectors.toList());
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable("id") Long id) {
        final Post post = postService.deletePost(id);
        return new ResponseEntity<>(PostDto.from(post), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<PostDto> editPost(@PathVariable Long id, @RequestBody PostDto postDto) {
        Post post = postService.getPostById(id);
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setUserId(postDto.getUserId());
        final Post editedPost = postService.update(post);
        return new ResponseEntity<>(PostDto.from(editedPost), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PostDto> creatPost(@RequestBody PostDto postDto) {
        postService.createPost(PostWrapper.mapToEntity(postDto));
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping(params = {"title"})
    public ResponseEntity<List<PostDto>> filter(@RequestParam(name = "title",required = true) String title) {
        List<Post> filteredPosts = postService.filter(title);
        List<PostDto> postsDto = filteredPosts.stream().map(PostDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void postNotFoundHandler(PostNotFoundException e) {
    }
}