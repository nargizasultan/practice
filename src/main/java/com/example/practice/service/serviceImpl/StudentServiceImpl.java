package com.example.practice.service.serviceImpl;

import com.example.practice.dto.PaginationResponse;
import com.example.practice.dto.SimpleResponse;
import com.example.practice.dto.StudentRequest;
import com.example.practice.dto.StudentResponse;
import com.example.practice.enity.Student;
import com.example.practice.exceptions.NotFoundException;
import com.example.practice.repository.StudentRepository;
import com.example.practice.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;


    @Override
    public SimpleResponse save(StudentRequest studentRequest) {

        Student student = new Student();

        student.setAge(studentRequest.getAge());
        student.setName(studentRequest.getName());
        student.setEmail(studentRequest.getEmail());
        student.setCreationDate(LocalDate.now());
        student.setGraduationDate(studentRequest.getGraduationDate());
        student.setBlocked(false);
        student.setPhoneNumber(studentRequest.getPhoneNumber());
        studentRepository.save(student);
        String massage=String.format("Student with id  %s successfully saved!", student.getId());
        log.info(massage);
        return new SimpleResponse(HttpStatus.OK, massage);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.getAllStudents();

    }

    @Override
    public StudentResponse getStudentById(Long id) {
        return studentRepository.getStudentById(id)
                .orElseThrow(() -> {
                    String message=String.format("Student with id : %s doesn't exists", id);
                    log.error(message);
                    return new NotFoundException(message);
                });


    }

    @Override
    public SimpleResponse update(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Student with id : %s doesn't exists", id);
                    log.error(message);
                    return new NotFoundException(message);
                });
        student.setAge(studentRequest.getAge());
        student.setName(studentRequest.getName());
        student.setEmail(studentRequest.getEmail());
        student.setCreationDate(LocalDate.now());
        student.setGraduationDate(studentRequest.getGraduationDate());
        student.setBlocked(false);
        student.setPhoneNumber(studentRequest.getPhoneNumber());
        studentRepository.save(student);
        String massage=String.format("Student with id  %s successfully updated!", student.getId());
        log.info(massage);
        return new SimpleResponse(HttpStatus.OK, massage);

    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            String massage=String.format("Student with id : %s doesn't exists", id);
            log.error(massage);
            throw new NotFoundException(massage);
        }
        studentRepository.deleteById(id);
        String massage=String.format("Student with id  %s successfully deleted!", id);
        log.info(massage);

        return new SimpleResponse(HttpStatus.OK,massage );

    }

    @Override
    public PaginationResponse getAllByPagination(int currentPage, int pageSize) {
        Pageable pageable= PageRequest.of(currentPage-1, pageSize);
        Page<StudentResponse> allStudents = studentRepository.getAllStudents(pageable);
        return new PaginationResponse(allStudents.getContent(),allStudents.getNumber()+1,allStudents.getTotalPages());
    }
}
