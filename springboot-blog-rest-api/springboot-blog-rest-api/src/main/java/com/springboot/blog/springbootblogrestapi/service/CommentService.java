package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId);

    CommentDto getCommentById(long postId,long commentId);

    CommentDto updateComment(Long postId,Long commentId,CommentDto commentRequest);

    void deleteCommentById(Long postId,Long commentId);
}
