package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private int age;
    private String email;
    private LocalDate createdAt;
    private LocalDate graduationDate;
    private boolean isBlocked;
    private String phoneNumber;

}
