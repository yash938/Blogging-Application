package com.BlogApp.controller;

import com.BlogApp.dto.CategoryDto;
import com.BlogApp.dto.PostDto;
import com.BlogApp.entity.Category;
import com.BlogApp.entity.Posts;
import com.BlogApp.service.PostService;
import com.BlogApp.utils.PaegableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable int postId, @RequestBody PostDto postDto){
        PostDto updatedPost = postService.updatePost(postId, postDto);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int postId){
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }


    //allPost
    @GetMapping
    public ResponseEntity<PaegableResponse<PostDto>> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir
    ){
        PaegableResponse<PostDto> allPosts = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(allPosts);
    }
    
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId){
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId){
        List<PostDto> posts = postService.getPostByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable int categoryId){
        List<PostDto> posts = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }
}
