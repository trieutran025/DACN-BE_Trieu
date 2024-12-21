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
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile; // Đổi từ User sang Profile

    @Id
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Classes classesEntity;

    // Getters and Setters
}
