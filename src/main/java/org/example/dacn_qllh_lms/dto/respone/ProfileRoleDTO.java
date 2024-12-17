package org.example.dacn_qllh_lms.dto.respone;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dacn_qllh_lms.entity.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileRoleDTO {
    Long id;
    String fullName;

    String phoneNumber;

    String address;
    Set<Role> roleName;
}
