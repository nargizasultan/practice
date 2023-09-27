package com.example.practice.enity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq",sequenceName = "student_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String email;
    private int age;
    private LocalDate creationDate;
    private LocalDate graduationDate;
    private boolean isBlocked;
    private String phoneNumber;
}
