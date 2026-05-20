package com.studentmgmt.repository;

import com.studentmgmt.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByStudentClass_ClassName(String className);

    Optional<Student> findByRollNo(String rollNo);

    boolean existsByRollNo(String rollNo);
}
