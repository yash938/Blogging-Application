package com.BlogApp.serviceImplementation;

import com.BlogApp.dto.PostDto;
import com.BlogApp.entity.Category;
import com.BlogApp.entity.Posts;
import com.BlogApp.entity.User;
import com.BlogApp.repository.CategoryRepo;
import com.BlogApp.repository.PostRepo;
import com.BlogApp.repository.UserRepo;
import com.BlogApp.service.PostService;
import com.BlogApp.utils.Helper;
import com.BlogApp.utils.PaegableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto) {
//        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        Posts post = modelMapper.map(postDto, Posts.class);
//        post.setUser(user);
//        post.setCategory(category);
        post.setPostDate(LocalDate.now());
        post.setPostImage("Image.png");
        Posts createdPost = postRepo.save(post);
        return modelMapper.map(createdPost, PostDto.class);
    }

    @Override
    public PostDto createWithCategory(PostDto postDto, int categoryId,int userId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Posts post = modelMapper.map(postDto, Posts.class);
        post.setPostDate(LocalDate.now());
        post.setCategory(category);
        post.setUser(user);

        Posts save = postRepo.save(post);
        return modelMapper.map(save, PostDto.class);
    }

    @Override
    public PostDto updatePost(int id, PostDto postDto) {
        Posts post = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setPostTitle(postDto.getTitle());
        post.setPostContent(postDto.getContent());
        post.setPostImage(postDto.getImageName());
        Posts updatePost = postRepo.save(post);
        return modelMapper.map(updatePost, PostDto.class);
    }

    @Override
    public PostDto assignPostToCategory(int postId, int categoryId) {
        Posts post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        post.setCategory(category);
        Posts updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public PostDto getPostById(int id) {
        Posts post = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public void deletePost(int id) {
        Posts post = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepo.delete(post);
    }

    @Override
    public PaegableResponse<PostDto> getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()): (Sort.by(sortBy).descending()) ;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Posts> allPosts = postRepo.findAll(pageRequest);
        PaegableResponse<PostDto> all = Helper.getPaegable(allPosts, PostDto.class);
        return all;
    }

    @Override
    public List<Posts> getPostByCategory(Category category) {

        return null;
    }

    @Override
    public List<Posts> getPostByUser(int userId) {
        return List.of();
    }
}
