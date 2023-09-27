package com.example.practice.repository;

import com.example.practice.dto.StudentResponse;
import com.example.practice.enity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select new com.example.practice.dto.StudentResponse(s.id, s.name, s.age, s.email, s.creationDate ,s.graduationDate, s.isBlocked, s.phoneNumber)  from Student  s ")
    List<StudentResponse> getAllStudents();

    @Query("select new com.example.practice.dto.StudentResponse(s.id, s.name, s.age, s.email, s.creationDate ,s.graduationDate, s.isBlocked, s.phoneNumber)  from Student  s where s.id=:studentId")
    Optional<StudentResponse> getStudentById(Long studentId);

    @Query("select new com.example.practice.dto.StudentResponse(s.id, s.name, s.age, s.email, s.creationDate ,s.graduationDate, s.isBlocked, s.phoneNumber)  from Student  s ")
    Page<StudentResponse> getAllStudents(Pageable pageable);
}
