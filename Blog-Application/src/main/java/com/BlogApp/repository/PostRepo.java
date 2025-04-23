package com.BlogApp.repository;

import com.BlogApp.entity.Category;
import com.BlogApp.entity.Posts;
import com.BlogApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Posts,Integer> {

    //find Post BY user;
    List<Posts> findByUser(User user);
    //find Post BY category;
    Optional<List<Posts>> findByCategory(Category category);

    List<Posts> findByPostTitleContaining(String Keyword);


}
