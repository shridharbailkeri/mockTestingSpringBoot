package com.alibou.example.student;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public record StudentDto(
        @NotEmpty(message = "First name should not be empty")
        String firstName,
        @NotEmpty
        String lastName,
        String email,
        Integer schoolId
) {
}
