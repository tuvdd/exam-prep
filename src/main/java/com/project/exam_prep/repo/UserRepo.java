package com.project.exam_prep.repo;

import com.project.exam_prep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> getUserByUsernameAndPassword(String username, String password);

    Optional<User> existsUserByUsernameAndPassword(String userName, String password);
}
