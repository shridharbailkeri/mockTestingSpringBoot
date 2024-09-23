package com.alibou.example.student;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    // private StudentMapper studentMapper; its a variable or a field its not instantiated
    // so add before each test and instantiate it
    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    public void shouldMapStudentDtoToStudent() {
        StudentDto studentDto = new StudentDto("John", "Doe", "john@g.com", 1);
        // perform the action of the actual method
        Student student = mapper.toStudent(studentDto); // means input should be same as out put no small letters in input and capital letters in output
        // check the outcome of the actual methods out put with original dto
        assertEquals(studentDto.firstName(), student.getFirstName());
        assertEquals(studentDto.lastName(), student.getLastName());
        assertEquals(studentDto.email(), student.getEmail());
        //student.setSchool(school); below line cheks if school is not null assertNotNull(student.getSchool());
        assertNotNull(student.getSchool());
        assertEquals(studentDto.schoolId(), student.getSchool().getId());

    }

    @Test
    public void should_throw_null_pointer_exception_when_studentDto_is_null() {
        var msg = assertThrows(NullPointerException.class, () -> mapper.toStudent(null));
        assertEquals("The student Dto should not be null", msg.getMessage());
    }

    @Test
    public void shouldMapStudentToStudentResponseDto() {
        // Given : a student object
        Student student = new Student("John", "Doe", "jd@gmx.com", 15);

        // When: we map student to StudentResponseDto object
        StudentResponseDto responseDto = mapper.toStudentResponseDto(student);
        // then i expect these results
        assertEquals(responseDto.firstName(), student.getFirstName());
        assertEquals(responseDto.lastName(), student.getLastName());
        assertEquals(responseDto.email(), student.getEmail());


    }
}