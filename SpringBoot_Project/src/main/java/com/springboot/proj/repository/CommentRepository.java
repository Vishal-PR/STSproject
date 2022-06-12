package com.springboot.proj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.proj.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByPostID(long postID);
}
