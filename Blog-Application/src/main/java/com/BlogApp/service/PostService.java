package com.BlogApp.service;

import com.BlogApp.dto.PostDto;
import com.BlogApp.entity.Category;
import com.BlogApp.entity.Posts;
import com.BlogApp.entity.User;
import com.BlogApp.utils.PaegableResponse;


import java.util.List;


public interface PostService {

    PostDto createPost(PostDto postDto);
    PostDto createWithCategory(PostDto postDto, int categoryId,int userId);
    PostDto updatePost(int id, PostDto postDto);

    PostDto assignPostToCategory(int postId,int categoryId);
    PostDto getPostById(int id);
    void deletePost(int id);
    PaegableResponse<PostDto> getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all post by category
    List<PostDto> getPostByCategory(int categoryId);
    //get all post by user
    List<PostDto> getPostByUser(int userId);

    List<PostDto> searchPost(String keyword);


    //search post by title

}
