package com.studentmgmt.controller;

import com.studentmgmt.dto.ClassRequestDTO;
import com.studentmgmt.dto.ClassResponseDTO;
import com.studentmgmt.service.ClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping
    public ResponseEntity<ClassResponseDTO> createClass(@Valid @RequestBody ClassRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(classService.createClass(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClassResponseDTO>> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassResponseDTO> getClassById(@PathVariable Long id) {
        return ResponseEntity.ok(classService.getClassById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassResponseDTO> updateClass(@PathVariable Long id,
                                                        @Valid @RequestBody ClassRequestDTO dto) {
        return ResponseEntity.ok(classService.updateClass(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}