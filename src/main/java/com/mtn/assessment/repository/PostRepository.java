package com.mtn.assessment.repository;

import com.mtn.assessment.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.title LIKE %?1%")
    List<Post> filter(@Param("title") String title);
    Post getPostById(Long id);
    Page<Post> findAll(Pageable pageable);
}
