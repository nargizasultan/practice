package com.example.practice.repository;


import com.example.practice.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select u from User u join u.posts p WHERE p.id = :postId")
    User findUserByPostId(Long postId);

    @Query("select case when count(r)>0 then true else false end from User r join r.follows f where r.id=:receiverId and f.id=:userId")
    boolean isUserFollowing(Long userId,Long receiverId);
}
