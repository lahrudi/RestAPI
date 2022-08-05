package com.mtn.assessment.repository;

import com.mtn.assessment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByPostId(Long postId);
    Page<Comment> findAll(Pageable pageable);
}
