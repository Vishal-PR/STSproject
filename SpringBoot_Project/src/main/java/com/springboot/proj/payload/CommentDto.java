package com.springboot.proj.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Api(value = "Comment model information")
@Data
public class CommentDto {

	@ApiModelProperty(value = "comment id")
	private long id;
	
	//name should not be Null or Empty
	@ApiModelProperty(value = "comment name")
	@NotEmpty(message = "name should not be Null or Empty")
	private String name;
	
	
	// email should not be null or empty
    // email field validation
	@ApiModelProperty(value = "Comment email")
	@NotEmpty(message = "email should not be null or empty")
	@Email
	private String email;
	
	
	// comment body should not be bull or empty
    // Comment body must be minimum 10 character
	@NotEmpty
	@ApiModelProperty(value = "comment body")
	@Size(min = 10, message = "comment body must be minimum 10 characters" )
	private String body;
	
}
