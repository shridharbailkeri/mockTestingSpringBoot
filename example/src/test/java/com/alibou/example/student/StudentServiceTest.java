package com.alibou.example.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class StudentServiceTest {

    // why mock testing
    // example student service, it has dependency on student repository , and student repository interacts with database
    // sometimes testing is carried out without database leading to isolated testing
    // isolated testing means you actually mock the behavior of the dependencies of student service because you carry out testing without actally
    // student repository (dependeny) actually interacting with database
    // means you can carry out mock testing student service even without a database

    // which service we want to test
    @InjectMocks
    private StudentService studentService;

    // declare the dependencies (to be mocked)
    // @Mock we tell our test class and mockito framework that we want to mock student repository and student mapper
    // how to tell mocking frame work we want to inject the below two dependencies into studentService? @InjectMocks
    // any dependencies annotated with @Mock will be injected to studentService now
    // then we need to tell mockito frame work that we need to open or start the mocks for this current class @befoe each
    //MockitoAnnotations.openMocks(this);
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_a_student() {
        // Given
        StudentDto studentDto = new StudentDto("John", "Doe", "john@g.com", 1);
        Student student = new Student("John", "Doe", "john@g.com", 20);
        // mock every call that uses another dependency
        Mockito.when(studentMapper.toStudent(studentDto)).thenReturn(student); // remeber we are resturning student that we created one line above
        // not the student actually saved in repository , Mockito.when we are mocking a method call
        // when we call student mapper tostudent method it needs to return student runs in isolation mode and it does not depend on real implementation
        // or the real instance of the student mapper
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        Mockito.when(studentMapper.toStudentResponseDto(student))
                .thenReturn(new StudentResponseDto("John", "Doe", "john@g.com"));

        // When
        StudentResponseDto responseDto = studentService.saveStudent(studentDto);

        //Then
        assertEquals(studentDto.firstName(), responseDto.firstName());
        assertEquals(studentDto.lastName(), responseDto.lastName());
        assertEquals(studentDto.email(), responseDto.email());

        Mockito.verify(studentMapper, Mockito.times(1))
                .toStudent(studentDto);
        Mockito.verify(studentRepository, Mockito.times(1))
                .save(student);
        Mockito.verify(studentMapper, Mockito.times(1))
                .toStudentResponseDto(student);

    }

    @Test
    public void should_return_all_students() {
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student("John", "Doe", "john@g.com", 20));

        // Mock the calls
        Mockito.when(studentRepository.findAll()).thenReturn(students);
        // when any student is passed
        Mockito.when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto("John", "Doe", "john@g.com"));

        //When
        List<StudentResponseDto> responseDtos = studentService.findAllStudents();

        // Then
        assertEquals(students.size(), responseDtos.size());

        Mockito.verify(studentRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void should_return_student_by_id() {
        // Given
        int studentId = 1;
        StudentResponseDto studentResponseDto = new StudentResponseDto("John", "Doe", "john@g.com");
        Student student = new Student("John", "Doe", "john@g.com", 20);

        // Mock the calls
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(studentMapper.toStudentResponseDto(any(Student.class))).thenReturn(studentResponseDto);

        //When
        StudentResponseDto studentResponseDto1 = studentService.findStudentById(studentId);

        //Then
        assertEquals(studentResponseDto.firstName(), studentResponseDto1.firstName());
        assertEquals(studentResponseDto.lastName(), studentResponseDto1.lastName());
        assertEquals(studentResponseDto.lastName(), studentResponseDto1.lastName());

        Mockito.verify(studentRepository, Mockito.times(1)).findById(studentId);

    }

    @Test
    public void should_find_student_by_name() {
        // Given
        String studentName = "John";
        List<Student> students = new ArrayList<>();
        students.add(new Student("John", "Doe", "john@g.com", 20));

        // Mock the calls
        Mockito.when(studentRepository.findAllByFirstNameContaining(studentName)).thenReturn(students);
        // when any student is passed
        Mockito.when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto("John", "Doe", "john@g.com"));

        // When
        var responseDto = studentService.findStudentByName(studentName);

        //Then
        assertEquals(students.size(), responseDto.size());

        Mockito.verify(studentRepository, Mockito.times(1)).findAllByFirstNameContaining(studentName);


    }
}