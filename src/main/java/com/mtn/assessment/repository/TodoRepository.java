package com.mtn.assessment.repository;

import com.mtn.assessment.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserIdAndCompleted(Long userId,Boolean completed);
}
