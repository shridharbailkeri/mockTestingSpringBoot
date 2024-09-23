package com.alibou.example.student;


import com.alibou.example.student.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class StudentProfile {

    @Id
    @GeneratedValue
    private Integer id;

    private String bio;

    // student is primary and student profile is secondary entity
    // so add join column here
    @OneToOne
    @JoinColumn(
            name = "student_id"
    )
    private Student student;

    public StudentProfile(String bio) {
        this.bio = bio;
    }


}
