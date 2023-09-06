package com.springboot.blog.springbootblogrestapi.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    @NotEmpty(message = "name should not be empty")

    private String name;
    @NotEmpty(message = "email should not be empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min=10,message = "body minimum size 10")
    private String body;
}
