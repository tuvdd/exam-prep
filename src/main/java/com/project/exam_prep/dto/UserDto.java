package com.project.exam_prep.dto;

import com.project.exam_prep.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String email;
    private String phoneNumber;
    private String address;
    private String role;
    private boolean is_active;

    public UserDto(User user){
        if(user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.profilePicture = user.getProfilePicture();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
            this.address = user.getAddress();
            this.role = user.getRole();
            this.is_active = user.is_active();
        }
    }
}
