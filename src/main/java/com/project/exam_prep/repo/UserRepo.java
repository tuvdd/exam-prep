package com.project.exam_prep.repo;

import com.project.exam_prep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    User getUserByUsername(String username);

    boolean existsUserByUsernameAndPassword(String userName, String password);

    User getUserById(Integer id);

    User findByUsername(String username);
}
