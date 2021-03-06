package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//the long represents the ID, or the point of reference
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(long id);
}
