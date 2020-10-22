package com.covid.controller;

import com.covid.model.Student;
import com.covid.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("user/data/student")
public class StudentManagementController {
    @Autowired
    private StudentRepository studentRepository;


//    hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Student> getAllStudents() {
        System.out.println("getAllStudents");
        return studentRepository.findAll();
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('admin:edit')")
    public ResponseEntity<Object> registerNewStudent(@RequestBody Student student) {
        System.out.println("registerNewStudent");
        System.out.println(student);
        Student savedStudent = studentRepository.save(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{studentId}")
                .buildAndExpand(savedStudent.getStudentId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('admin:edit')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println("deleteStudent");
        System.out.println(studentId);
        studentRepository.deleteById(studentId);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('admin:edit')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println("updateStudent");
        System.out.println(String.format("%s %s", studentId, student));
        studentRepository.save(student);
    }
}