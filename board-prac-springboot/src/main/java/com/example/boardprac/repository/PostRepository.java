package com.example.boardprac.repository;

import com.example.boardprac.domain.Post;
import com.example.boardprac.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByTitleContaining(String title, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    List<Post> findAllByUser(User user);
}
