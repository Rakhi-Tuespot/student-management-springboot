package com.studentmgmt.service;

import com.studentmgmt.dto.ClassRequestDTO;
import com.studentmgmt.dto.ClassResponseDTO;
import com.studentmgmt.dto.StudentResponseDTO;
import com.studentmgmt.exception.DuplicateResourceException;
import com.studentmgmt.exception.ResourceNotFoundException;
import com.studentmgmt.model.ClassEntity;
import com.studentmgmt.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;

    public ClassResponseDTO createClass(ClassRequestDTO dto) {
        if (classRepository.existsByClassName(dto.getClassName())) {
            throw new DuplicateResourceException("Class '" + dto.getClassName() + "' already exists");
        }
        ClassEntity entity = new ClassEntity();
        entity.setClassName(dto.getClassName());
        return toResponseDTO(classRepository.save(entity));
    }

    public List<ClassResponseDTO> getAllClasses() {
        return classRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClassResponseDTO getClassById(Long id) {
        return toResponseDTO(classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id)));
    }

    public ClassResponseDTO updateClass(Long id, ClassRequestDTO dto) {
        ClassEntity existing = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));

        if (!existing.getClassName().equals(dto.getClassName())
                && classRepository.existsByClassName(dto.getClassName())) {
            throw new DuplicateResourceException("Class '" + dto.getClassName() + "' already exists");
        }
        existing.setClassName(dto.getClassName());
        return toResponseDTO(classRepository.save(existing));
    }

    public void deleteClass(Long id) {
        ClassEntity existing = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));
        classRepository.delete(existing);
    }

    private ClassResponseDTO toResponseDTO(ClassEntity c) {
        List<StudentResponseDTO> students = c.getStudents() == null ? List.of() :
                c.getStudents().stream()
                .map(s -> new StudentResponseDTO(
                        s.getId(),
                        s.getName(),
                        s.getRollNo(),
                        s.getStudentClass().getId(),
                        s.getStudentClass().getClassName()
                ))
                .collect(Collectors.toList());

        return new ClassResponseDTO(c.getId(), c.getClassName(), students);
    }
}