package com.studentmgmt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StudentRequestDTO {

    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotBlank(message = "Roll number should not be blank")
    @Pattern(regexp = "^[A-Z0-9]{3,10}$", message = "Roll number must be 3–10 uppercase alphanumeric characters (e.g. STU001)")
    private String rollNo;

    @NotNull(message = "Class ID must not be null")
    private Long classId;
}
