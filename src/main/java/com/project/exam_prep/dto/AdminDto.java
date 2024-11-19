package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class AdminDto {
    private Integer id;
    private Date createdAt;
    private Date lastLogin;
    private UserDto userDto;

    public AdminDto(Admin admin) {
        if(admin != null) {
            this.id = admin.getId();
            this.createdAt = admin.getCreatedAt();
            this.lastLogin = admin.getLastLogin();
            this.userDto = new UserDto(admin.getUser());
        }
    }
}
