package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId")  long postId,
                                                   @Valid @RequestBody CommentDto commentDto){

        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);

    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable (value = "postId" )Long postId){

        return  commentService.getCommentByPostId(postId);

    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>  getCommentsById(@PathVariable (value = "postId" )Long postId,@PathVariable(value = "id") Long commentId){
      CommentDto commentDto  =commentService.getCommentById(postId,commentId);
        return  new ResponseEntity<>(commentDto,HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable (value = "postId" )Long postId,@PathVariable(value = "id") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto){

        return  new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDto),HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public  ResponseEntity<String> deleteCommentById(@PathVariable (value = "postId" )Long postId,@PathVariable(value = "id") Long commentId){
        commentService.deleteCommentById(postId,commentId);
        return   new ResponseEntity<>("Succesfully Deleted",HttpStatus.OK);
    }


}
