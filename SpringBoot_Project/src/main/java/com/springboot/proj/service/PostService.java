package com.springboot.proj.service;

import com.springboot.proj.payload.PostDto;
import com.springboot.proj.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPosts(int pageNO, int pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(long id);
	
	PostDto updatePost(PostDto postDto, long id);
	
	void deletePostById(long id);
}
