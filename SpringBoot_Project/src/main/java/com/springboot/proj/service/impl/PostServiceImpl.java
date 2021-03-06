package com.springboot.proj.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.proj.entity.Post;
import com.springboot.proj.exception.ResourceNotFoundException;
import com.springboot.proj.payload.PostDto;
import com.springboot.proj.payload.PostResponse;
import com.springboot.proj.repository.PostRepository;
import com.springboot.proj.service.PostService;

@Service
public class PostServiceImpl  implements PostService{
	
	private  PostRepository postRepository;
	
	private ModelMapper mapper;

	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		this.postRepository = postRepository;
		this.mapper = mapper;
	}
	@Override
	public PostDto createPost(PostDto postDto) {
		
		//convert DTO to Entity
		Post post = mapToEntity(postDto);
		Post newpost = postRepository.save(post);
		
		//Convert entity to DTO
		PostDto postresponse = mapToDTO(newpost);
		return postresponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort =sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		
		//create Pageable instance
		
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
	
		Page<Post> posts = postRepository.findAll(pageable);
		
		
		//get content for page object
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDto> content = listOfPosts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        
		return postResponse;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id", id));
	
		return mapToDTO(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		//get post by id from database
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id", id));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatedPost = postRepository.save(post);
		
		return mapToDTO(updatedPost);
	}

	@Override
	public void deletePostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id", id));
		postRepository.delete(post);
		
	}
	
	//Convert Entity to DTO
	private PostDto mapToDTO(Post post) {
		PostDto postDto = mapper.map(post, PostDto.class);
		
		return postDto;
	}
	
	private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
}
}

