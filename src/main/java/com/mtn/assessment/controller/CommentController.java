package com.mtn.assessment.controller;

import com.mtn.assessment.commons.CommentWrapper;
import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.dto.CommentDto;
import com.mtn.assessment.exception.CommentNotFoundException;
import com.mtn.assessment.payload.CommentResponse;
import com.mtn.assessment.service.CommentService;
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
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Value("${error.message}")
    private String errorMessage;

    @GetMapping(params = {"pageNo", "pageSize", "sortBy", "sortDir"})
    public ResponseEntity<CommentResponse> findPaginated(@RequestParam(name = "pageNo",value = "pageNo") int pageNo,
                                                         @RequestParam(name = "pageSize",value = "pageSize") int pageSize,
                                                         @RequestParam(name = "sortBy",value = "sortBy",required = false) String sortBy,
                                                         @RequestParam(name = "sortBy",value = "sortBy",required = false) String sortDir) {
        CommentResponse commentResponse = commentService.findPaginated(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<CommentDto>> getComments() {
        List<Comment> comments = commentService.getAll();
        List<CommentDto> commentDtos = comments.stream().map(CommentDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id) {
        Comment comment = commentService.getCommentById(id);
        return new ResponseEntity<>(CommentWrapper.mapToDTO(comment), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CommentDto> editComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        Comment comment = commentService.getCommentById(id);
        comment.setPostId(commentDto.getPostId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        final Comment editedComment = commentService.update(comment);
        return new ResponseEntity<>(CommentDto.from(editedComment), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable("id") Long id) {
        final Comment comment = commentService.deleteComment(id);
        return new ResponseEntity<>(CommentDto.from(comment), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CommentDto> creatPost(@RequestBody CommentDto commentDto) {
        commentService.createComment(CommentWrapper.mapToEntity(commentDto));
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @GetMapping(params = {"{postId}"})
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@RequestParam(name = "postId",required = true) Long postId) {
        final List<Comment> comments = commentService.getCommentByPostId(postId);
        List<CommentDto> commentDtoList = comments.stream().map(comment -> CommentWrapper.mapToDTO(comment)).collect(Collectors.toList());
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void commentNotFoundHandler(CommentNotFoundException e) {
    }
}