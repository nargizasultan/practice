package com.example.practice.dto;

import com.example.practice.validation.PhoneNumberValidation;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    private String name;
    @Email(message = "")
    private String email;
    private int age;
    private LocalDate graduationDate;
    @PhoneNumberValidation
    private String phoneNumber;

}
