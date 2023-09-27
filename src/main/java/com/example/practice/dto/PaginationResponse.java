package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {
    private List<StudentResponse>students;
    private int currantPage;
    private int pageSize;
}
