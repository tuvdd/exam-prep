package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.AdminDto;
import com.project.exam_prep.entity.Admin;

import com.project.exam_prep.entity.User;
import com.project.exam_prep.repo.AdminRepo;
import com.project.exam_prep.repo.UserRepo;
import com.project.exam_prep.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public boolean addAdmin(AdminDto adminDto) {
        if (adminDto.getUserDto().getUsername() == null || adminDto.getUserDto().getPassword() == null) {
            return false;
        }
        String encodePassword = encoder.encode(adminDto.getUserDto().getPassword());
        if(userRepo.existsUserByUsernameAndPassword(adminDto.getUserDto().getUsername(), encodePassword)) {
            return false;
        }

        User user = new User(
                adminDto.getUserDto().getId(),
                adminDto.getUserDto().getUsername(),
                encodePassword,
                adminDto.getUserDto().getFirstName(),
                adminDto.getUserDto().getLastName(),
                adminDto.getUserDto().getProfilePicture(),
                adminDto.getUserDto().getEmail(),
                adminDto.getUserDto().getPhoneNumber(),
                adminDto.getUserDto().getAddress(),
                "ROLE_ADMIN",
                true
        );

        userRepo.save(user);

        Admin admin;
        admin = new Admin(
                adminDto.getId(),
                new Date(), null, user
        );
        adminRepo.save(admin);
        return true;
    }

    @Override
    public AdminDto updateLastLogin(Integer userId) {
        Admin admin = adminRepo.getAdminByUserId(userId);
        admin.setLastLogin(new Date());
        adminRepo.save(admin);
        return new AdminDto(adminRepo.getAdminByUserId(userId));
    }


}
