package com.BlogApp.controller;

import com.BlogApp.dto.CommentDto;
import com.BlogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable int postId,  @RequestBody CommentDto commentDto){
        CommentDto comments = commentService.createCommentWithPostAndUserId(commentDto, postId);
        return ResponseEntity.ok(comments);
    }
}
