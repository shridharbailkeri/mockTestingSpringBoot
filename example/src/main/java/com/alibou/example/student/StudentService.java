package com.alibou.example.student;

import com.alibou.example.student.StudentDto;
import com.alibou.example.student.StudentMapper;
import com.alibou.example.student.StudentRepository;
import com.alibou.example.student.StudentResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    private final StudentMapper studentMapper;

    public StudentResponseDto saveStudent(StudentDto studentDto) {
        var student = studentMapper.toStudent(studentDto);
        var savedStudent = repository.save(student);
        return studentMapper.toStudentResponseDto(savedStudent);
    }

    public List<StudentResponseDto> findAllStudents() {

        return repository.findAll()
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .collect(Collectors.toList());
    }

    public StudentResponseDto findStudentById(Integer id) {
        return repository.findById(id)
                .map(studentMapper::toStudentResponseDto)
                .orElse(null);
    }

    public List<StudentResponseDto> findStudentByName(String name) {
        return repository.findAllByFirstNameContaining(name)
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .collect(Collectors.toList());
    }

    public void deletetById(Integer id) {
        repository.deleteById(id);
    }
}
