package com.example.carwash.repository.community;

import com.example.carwash.domain.comment.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReCommentRepository extends JpaRepository<ReComment,Long> {
}
