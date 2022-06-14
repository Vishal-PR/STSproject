package com.springboot.proj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.proj.payload.PostResponse;
import com.springboot.proj.service.PostService;

@RestController
@RequestMapping
public class PostController {

	
	PostService postService;
	
	public PostController(PostService postService) {
		this.postService= postService;
	}
	

}
