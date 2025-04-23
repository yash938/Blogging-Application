package com.BlogApp.controller;

import com.BlogApp.dto.CategoryDto;
import com.BlogApp.dto.ImageResponse;
import com.BlogApp.dto.PostDto;
import com.BlogApp.dto.UserDto;
import com.BlogApp.entity.Category;
import com.BlogApp.entity.Posts;
import com.BlogApp.service.FileService;
import com.BlogApp.service.PostService;
import com.BlogApp.service.UserService;
import com.BlogApp.utils.PaegableResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {

    @Value("${user.post.image.path}")
    private String imageUploadPath;
    @Autowired
    private PostService postService;

    private FileService imageFile;

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
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword){
        List<PostDto> posts = postService.searchPost(keyword);
        return ResponseEntity.ok(posts);
    }

    //Image Upload
    @PostMapping("/image/{id}")
    public ResponseEntity<ImageResponse> uploadFile(@PathVariable int id, @RequestParam("image")MultipartFile image){
        String imageName = imageFile.UploadFile(image, imageUploadPath);

        PostDto postById = postService.getPostById(id);
        postById.setImageName(imageName);
//        UserDto userDto = userService.updateUser(singleUser, id);
        PostDto postDto = postService.updatePost(id, postById);

        ImageResponse imageResponse = new ImageResponse(imageName,"Image upload successfully",true,HttpStatus.OK, LocalDate.now());
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    @GetMapping("/image/{id}")
    public void ServeImage(@PathVariable int id, HttpServletResponse response) throws IOException {
//        UserDto singleUser = userService.getSingleUser(id);
        PostDto postById = postService.getPostById(id);
        log.info("user image name {}",postById.getImageName());
        InputStream resource = imageFile.getResource(imageUploadPath, postById.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response. getOutputStream());
    }
}
