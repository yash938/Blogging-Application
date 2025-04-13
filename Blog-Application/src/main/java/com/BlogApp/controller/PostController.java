package com.BlogApp.controller;

import com.BlogApp.dto.PostDto;
import com.BlogApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto post = postService.createPost(postDto);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/user/{userId}/{categoryId}")
    public ResponseEntity<PostDto> createPostWithCategory(@RequestBody PostDto postDto, @PathVariable int categoryId,@PathVariable int userId){
        PostDto post = postService.createWithCategory(postDto, categoryId, userId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}/category/{categoryId}")
    public ResponseEntity<PostDto> assignPostToCategory(@PathVariable int postId, @PathVariable int categoryId){
        PostDto postDto = postService.assignPostToCategory(postId, categoryId);
        return ResponseEntity.ok(postDto);
    }
}
