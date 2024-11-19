package com.project.exam_prep.repo;

import com.project.exam_prep.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

    Admin getAdminByUserId(Integer id);
}
