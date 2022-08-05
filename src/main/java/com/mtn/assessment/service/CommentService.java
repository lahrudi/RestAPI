package com.mtn.assessment.service;

import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.dto.CommentDto;
import com.mtn.assessment.exception.CommentNotFoundException;
import com.mtn.assessment.payload.CommentResponse;
import com.mtn.assessment.repository.CommentRepository;
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

import static com.mtn.assessment.commons.CommentWrapper.mapToDTO;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
@Slf4j
@Service
public class CommentService {
    private final RestTemplate restTemplate;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.commentRepository = commentRepository;
    }

    @Cacheable("comments")
    public Comment getCommentById(Long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    public List<Comment> getCommentByPostId(Long postId) {
        return commentRepository
                .findByPostId(postId)
                .orElseThrow(() -> new CommentNotFoundException(postId));
    }

    public void loadCommentsData(String url) throws IOException {
        Stream commentStream = Arrays.stream(restTemplate.getForObject(url, Comment[].class));
        commentStream.forEach(comment -> createDocument((Comment) comment));
    }

    public Comment createDocument(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getAll() {
        return StreamSupport.stream(commentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public CommentResponse findPaginated(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("findPaginated method of CommentService class calls with pageNumber : {} and pageSize : {}", pageNumber, pageSize);

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

        Page<Comment> comments = commentRepository.findAll(pageRequest);

        List<Comment> commentList = comments.getContent();

        List<CommentDto> content = commentList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(content);
        commentResponse.setPageNo(comments.getNumber());
        commentResponse.setPageSize(comments.getSize());
        commentResponse.setTotalElements(comments.getTotalElements());
        commentResponse.setTotalPages(comments.getTotalPages());
        commentResponse.setLast(comments.isLast());

        return commentResponse;
    }

    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment deleteComment(Long id) {
        Comment comment = getCommentById(id);
        commentRepository.delete(comment);
        return comment;
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

}
