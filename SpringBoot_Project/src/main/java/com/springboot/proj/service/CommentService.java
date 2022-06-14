package com.springboot.proj.service;

import java.util.List;

import com.springboot.proj.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(Long postId,CommentDto commentdto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(Long postId, Long commentId);

	
	CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest);
	
	void deleteComment(Long postId, Long commentId);
}
