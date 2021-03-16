package com.example.bmshop.repository;

import com.example.bmshop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRespository extends JpaRepository<Comment, Long> {
}
