package com.example.practice.dto;

import com.example.practice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String name;
    private String email;
    private int age;
    private String password;
    private Role role;


}
