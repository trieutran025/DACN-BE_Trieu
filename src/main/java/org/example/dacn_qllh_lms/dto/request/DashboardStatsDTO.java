package org.example.dacn_qllh_lms.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardStatsDTO {
    private long totalClasses;
    private long totalStudents;
    private long totalInstructors;
}
