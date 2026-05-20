package com.studentmgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassResponseDTO {
    private Long id;
    private String className;
    private List<StudentResponseDTO> students;
}