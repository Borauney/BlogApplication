package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
     PostDto getPostById(long id);
    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir );
    PostDto updatePost(PostDto postDto,long id);

    void deleteById(long id);
}
