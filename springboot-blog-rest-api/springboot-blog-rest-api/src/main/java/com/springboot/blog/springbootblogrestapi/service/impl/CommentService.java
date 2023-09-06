package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements com.springboot.blog.springbootblogrestapi.service.CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentService(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
      Comment comment =mapToEntity(commentDto);
      //retreive posst entity by id
        Post post =postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        //save Comment entity
        Comment createdComment= commentRepository.save(comment);
        return mapToDto(createdComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
       //retreive comments by post id
        List<Comment> comments =commentRepository.findByPostId(postId);
        //convertlist of comment entities
        return comments.stream().map(comment -> mapToDto(comment)).toList();
    }
    public CommentDto getCommentById(long postId,long commentId) {
        //retreive comments by post id
        Post post =postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        //convertlist of comment entities
    if(!comment.getPost().getId().equals(post.getId())){
        throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
    }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        Post post =postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        //convertlist of comment entities
        if(!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }


        comment.setName(commentRequest.getName());
        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());
        comment.setPost(post);
        Comment updatedComment=   commentRepository.save(comment);

        return mapToDto(updatedComment);

    }


 @Override
    public void  deleteCommentById(Long postId,Long commentId){
            Post post =postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
            Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
     if(!comment.getPost().getId().equals(post.getId())){
         throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
     }
            commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
      //  CommentDto commentDto= new CommentDto();
      //  commentDto.setId(comment.getId());
       // commentDto.setName(comment.getName());
       // commentDto.setBody(comment.getBody());
       // commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto,Comment.class);
       // Comment comment= new Comment();
       // comment.setId(commentDto.getId());
        //comment.setName(commentDto.getName());
        //comment.setBody(commentDto.getBody());
        //comment.setEmail(commentDto.getEmail());
        return comment;
    }
}
