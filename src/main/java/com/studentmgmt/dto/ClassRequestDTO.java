package com.studentmgmt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassRequestDTO {

    @NotBlank(message = "Class name should not be blank")
    private String className;
}
