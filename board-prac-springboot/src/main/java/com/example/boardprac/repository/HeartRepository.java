package com.example.boardprac.repository;

import com.example.boardprac.domain.Heart;
import com.example.boardprac.domain.Post;
import com.example.boardprac.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserAndPost(User user, Post post);
    List<Heart> findAllByUser(User user);
}
