package com.userManagement.userManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
