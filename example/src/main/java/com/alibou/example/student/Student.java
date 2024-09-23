package com.alibou.example.student;

import com.alibou.example.school.School;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
//@Entity is required to persist to the database, when you use @Entity class must have no args constructor
// each field of the enity is the column, each instance of entity is the row
@Table(name = "T_STUDENT")
public class Student {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(
            name = "c_fname"
    )
    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private int age;

    @OneToOne(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private StudentProfile studentProfile;

   @ManyToOne
   @JoinColumn(
           name = "school_id"
   )
   @JsonBackReference
    private School school;

    public Student(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }
}
