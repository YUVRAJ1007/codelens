package com.codelens.repository;

import com.codelens.model.CodeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CodeReviewRepository extends JpaRepository<CodeReview, Long> {
    List<CodeReview> findBySessionIdOrderByCreatedAtDesc(String sessionId);
}
