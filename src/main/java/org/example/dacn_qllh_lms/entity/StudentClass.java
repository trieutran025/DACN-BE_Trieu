package org.example.dacn_qllh_lms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "students_classes")
public class StudentClass {
    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Id
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class classEntity;

    // Getters and Setters
}
