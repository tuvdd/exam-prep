package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.UserDto;
import com.project.exam_prep.entity.User;
import com.project.exam_prep.repo.UserRepo;
import com.project.exam_prep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public List<UserDto> getAllUser() {
        List<UserDto> result = new ArrayList<>();
        List<User> users = userRepo.findAll();

        for(User user: users) {
            result.add(new UserDto(user));
        }

        return result;
    }

    @Override
    public boolean addUser(UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null) {
            return false;
        }

        String encodePassword = encoder.encode(userDto.getPassword());
        if(userRepo.existsUserByUsernameAndPassword(userDto.getUsername(), encodePassword).isPresent()) {
            return false;
        }

        User user = new User(
            userDto.getId(),
            userDto.getUsername(),
            encodePassword,
            userDto.getFirstName(),
            userDto.getLastName(),
            userDto.getProfilePicture(),
            userDto.getEmail(),
            userDto.getPhoneNumber(),
            userDto.getAddress(),
            userDto.getRole()
        );
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        if(userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            return true;
        }
        else return false;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null) {
            return null;
        }
        String encodePassword = encoder.encode(userDto.getPassword());
        Optional<User> userOptional = userRepo.getUserByUsernameAndPassword(userDto.getUsername(), encodePassword);
        if(userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        user.setPassword(encodePassword);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setProfilePicture(userDto.getProfilePicture());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole(user.getRole());

        userRepo.save(user);
        return new UserDto(user);
    }

    @Override
    public UserDto login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        String encodePassword = encoder.encode(password);
        Optional<User> userOptional = userRepo.getUserByUsernameAndPassword(username, encodePassword);
        if(userOptional.isEmpty()) {
            return null;
        }
        return new UserDto(userOptional.get());
    }
}
