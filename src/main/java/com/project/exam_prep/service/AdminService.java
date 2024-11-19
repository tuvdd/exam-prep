package com.project.exam_prep.service;

import com.project.exam_prep.dto.AdminDto;

public interface AdminService {
    boolean addAdmin(AdminDto adminDto);

    AdminDto updateLastLogin(Integer userId);
}
