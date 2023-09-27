package com.example.practice.service;

import com.example.practice.dto.PaginationResponse;
import com.example.practice.dto.SimpleResponse;
import com.example.practice.dto.StudentRequest;
import com.example.practice.dto.StudentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

     SimpleResponse save(StudentRequest studentRequest);
     List<StudentResponse>getAllStudents();

     StudentResponse getStudentById(Long id);

     SimpleResponse update(Long id, StudentRequest studentRequest);

     SimpleResponse deleteById(Long id);



    PaginationResponse getAllByPagination(int currentPage, int pageSize);
}
