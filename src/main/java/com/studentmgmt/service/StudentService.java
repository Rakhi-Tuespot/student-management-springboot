package com.studentmgmt.service;

import com.studentmgmt.dto.StudentRequestDTO;
import com.studentmgmt.dto.StudentResponseDTO;
import com.studentmgmt.exception.DuplicateResourceException;
import com.studentmgmt.exception.ResourceNotFoundException;
import com.studentmgmt.model.ClassEntity;
import com.studentmgmt.model.Student;
import com.studentmgmt.repository.ClassRepository;
import com.studentmgmt.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    public StudentResponseDTO createStudent(StudentRequestDTO dto) {
        if (studentRepository.existsByRollNo(dto.getRollNo())) {
            throw new DuplicateResourceException("Student with roll number '" + dto.getRollNo() + "' already exists");
        }
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + dto.getClassId()));

        Student student = new Student();
        student.setName(dto.getName());
        student.setRollNo(dto.getRollNo());
        student.setStudentClass(classEntity);

        Student saved = studentRepository.save(student);
        return toResponseDTO(saved);
    }

    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return toResponseDTO(student);
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        if (!existing.getRollNo().equals(dto.getRollNo())
                && studentRepository.existsByRollNo(dto.getRollNo())) {
            throw new DuplicateResourceException("Student with roll number '" + dto.getRollNo() + "' already exists");
        }

        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + dto.getClassId()));

        existing.setName(dto.getName());
        existing.setRollNo(dto.getRollNo());
        existing.setStudentClass(classEntity);

        return toResponseDTO(studentRepository.save(existing));
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    public List<StudentResponseDTO> getStudentsByClassName(String className) {
        // Validate that class exists
        classRepository.findByClassName(className)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found: " + className));
        return studentRepository.findByStudentClass_ClassName(className)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public StudentResponseDTO getStudentByRollNo(String rollNo) {
        Student student = studentRepository.findByRollNo(rollNo)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with roll number: " + rollNo));
        return toResponseDTO(student);
    }

    private StudentResponseDTO toResponseDTO(Student s) {
        return new StudentResponseDTO(
                s.getId(),
                s.getName(),
                s.getRollNo(),
                s.getStudentClass().getId(),
                s.getStudentClass().getClassName()
        );
    }
}
