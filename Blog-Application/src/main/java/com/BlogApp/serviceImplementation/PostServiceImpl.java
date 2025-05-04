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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


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
  public List<PostDto> getPostByCategory(int categoryId) {
      // Fetch the category by ID
      Category category = categoryRepo.findById(categoryId)
              .orElseThrow(() -> new RuntimeException("Category not found"));

      // Fetch posts by category
      List<Posts> posts = postRepo.findByCategory(category)
              .orElseThrow(() -> new RuntimeException("Post not found"));

      // Map each post to PostDto
      return posts.stream()
              .map(post -> modelMapper.map(post, PostDto.class))
              .collect(Collectors.toList());
  }

  @Override
  public List<PostDto> getPostByUser(int userId) {
      // Fetch the user by ID
      User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

      // Fetch posts by user
      List<Posts> posts = postRepo.findByUser(user);

      // Map each post to PostDto
      return posts.stream()
              .map(post -> modelMapper.map(post, PostDto.class))
              .collect(Collectors.toList());
  }

  @Override
  public List<PostDto> searchPost(String keyword) {
      // Fetch posts containing the keyword in the title
      List<Posts> allPosts = postRepo.findByPostTitleContaining(keyword);

      // Map each post to PostDto
      return allPosts.stream()
              .map(post -> modelMapper.map(post, PostDto.class))
              .collect(Collectors.toList());
  }


}
