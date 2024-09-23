package com.alibou.example.school;

import com.alibou.example.student.Student;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class School {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(
            mappedBy = "school"
    )
    @JsonManagedReference // parent has to serialize the child, schoolo is parent of the child
    // parent is incharge of serializing the child and it prevents child from serializing the parent
    private List<Student> students;

    public School(String name) {
        this.name = name;
    }
}
