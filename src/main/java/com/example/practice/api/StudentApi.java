package com.example.practice.api;

import com.example.practice.dto.PaginationResponse;
import com.example.practice.dto.SimpleResponse;
import com.example.practice.dto.StudentRequest;
import com.example.practice.dto.StudentResponse;
import com.example.practice.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentApi {

    private final StudentService studentService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveStudent(@RequestBody @Valid StudentRequest studentRequest) {
        return studentService.save(studentRequest);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();

    }

    @GetMapping("/{studentId}")
    public StudentResponse getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @PutMapping("{studentId}")
    public SimpleResponse updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequest studentRequest) {
        return studentService.update(studentId, studentRequest);
    }

    @DeleteMapping("{studentId}")
    public SimpleResponse deleteStudent(@PathVariable Long studentId) {
        return studentService.deleteById(studentId);
    }

    @GetMapping("/pagination")
    public PaginationResponse pagination(@RequestParam(required = false) int currentPage, @RequestParam(required = false) int pageSize) {
        return studentService.getAllByPagination(currentPage, pageSize);
    }

}
