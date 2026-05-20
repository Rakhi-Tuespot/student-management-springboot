    package com.studentmgmt.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;

    @Entity
    @Table(name = "classes")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ClassEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Class name should not be blank")
        @Column(unique = true, nullable = false)
        private String className;

        @OneToMany(mappedBy = "studentClass", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Student> students;
    }
