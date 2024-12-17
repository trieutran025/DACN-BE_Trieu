package org.example.dacn_qllh_lms.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleDTO {
     private Long profileId;
     private String fullName;
     private String phoneNumber;
     private String address;
     private Long userId;
     private Long roleId;
}
