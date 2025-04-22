package com.coconutslices.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
