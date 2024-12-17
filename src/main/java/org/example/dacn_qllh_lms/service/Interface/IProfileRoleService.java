package org.example.dacn_qllh_lms.service.Interface;


import org.example.dacn_qllh_lms.dto.respone.ProfileRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IProfileRoleService {
    Page<ProfileRoleDTO> findAll(Pageable pageable);
    void updateProfileAndRole(Long profileId, String fullName, String phoneNumber, String address, Long userId,Long roleId);
}
