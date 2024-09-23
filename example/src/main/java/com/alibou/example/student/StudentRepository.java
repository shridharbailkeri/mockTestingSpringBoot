package com.alibou.example.student;

import com.alibou.example.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByFirstName(String name);

    List<Student> findAllByFirstNameContaining(String name);
}
