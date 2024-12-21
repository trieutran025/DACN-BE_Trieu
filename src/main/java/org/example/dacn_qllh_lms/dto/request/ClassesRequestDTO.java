    package org.example.dacn_qllh_lms.dto.request;

    import lombok.*;
    import lombok.experimental.FieldDefaults;
    import org.example.dacn_qllh_lms.entity.Profile;

    import java.time.LocalDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class ClassesRequestDTO {
        String className;
        String classCode;
        String description;
//        LocalDate startDate;
//        LocalDate endDate;
        Profile teacherId;

    }
