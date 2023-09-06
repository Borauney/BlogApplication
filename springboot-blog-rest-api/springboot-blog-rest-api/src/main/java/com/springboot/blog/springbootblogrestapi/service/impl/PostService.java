package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService implements com.springboot.blog.springbootblogrestapi.service.PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;


    public PostService(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
         //convert Dto to entity
        Post post = mapToEntity(postDto);
        Post newPost=postRepository.save(post);

        //convert entity to dto
        PostDto postResponse =mapToDto(newPost);

        return postResponse;
    }

    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir){
        Sort sort =sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable=PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content=  listOfPosts.stream().map(post-> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return  postResponse;
    }
    public PostDto getPostById(long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);

    }

    public void deleteById(long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);


    }



    public PostDto updatePost(PostDto postDto,long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);

        return  mapToDto(updatedPost);
    }

    private  PostDto mapToDto(Post post){
       PostDto postDto =mapper.map(post,PostDto.class);

       // PostDto postDto =new PostDto();
        //postDto.setId(post.getId());
        // PostDto postDto =new PostDto(); postDto.setTitle(post.getTitle());
        // PostDto postDto =new PostDto(); postDto.setDescription(post.getDescription());
        // PostDto postDto =new PostDto();  postDto.setContent(post.getContent());

        return postDto;

    }

    private  Post mapToEntity(PostDto postDto){
        Post post =mapper.map(postDto,Post.class);
       // Post post =new Post();
        //post.setTitle(postDto.getTitle());
        //post.setDescription(postDto.getDescription());
        //post.setContent(postDto.getContent());

        return post;

    }



}
