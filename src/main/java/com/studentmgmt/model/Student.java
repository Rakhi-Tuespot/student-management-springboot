package com.studentmgmt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name should not be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Roll number should not be blank")
    @Pattern(regexp = "^[A-Z0-9]{3,10}$", message = "Roll number must be 3–10 alphanumeric uppercase characters (e.g. STU001)")
    @Column(unique = true, nullable = false)
    private String rollNo;


    @NotNull(message = "Class must not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false)

    private ClassEntity studentClass;
}
