package com.springboot.proj.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import com.springboot.proj.entity.Comment;
import com.springboot.proj.entity.Post;
import com.springboot.proj.exception.BlogAPIException;
import com.springboot.proj.exception.ResourceNotFoundException;
import com.springboot.proj.payload.CommentDto;
import com.springboot.proj.repository.CommentRepository;
import com.springboot.proj.repository.PostRepository;
import com.springboot.proj.service.CommentService;

public class CommentServiceImpl implements CommentService{
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}
	
	@Override
	public CommentDto createComment(Long postId, CommentDto commentdto) {
		Comment comment = mapToEntity(commentdto);
		
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		comment.setPost(post);
		
		
		Comment newComment = commentRepository.save(comment);
		
		return null;
	}

	

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		//retrive comment by post id
		List<Comment>  comments = commentRepository.findByPostId(postId);
		
		
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
		
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id", postId));

		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
		
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment Does not belong to post");
		}
		return mapToDTO(comment);
	}
	
	@Override
	public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
		//retrive post
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		
		//retrive comment
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment Does not belong to post");
		}
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());

		Comment updatedComment = commentRepository.save(comment);
		
		
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		//retrive post
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
				
		//retrive comment
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment Does not belong to post");
		}
		commentRepository.delete(comment);
		// TODO Auto-generated method stub
		
	}
	
	private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return  commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return  comment;
    }

	
	
	

}
