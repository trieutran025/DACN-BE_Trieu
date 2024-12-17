package org.example.dacn_qllh_lms.service.Impl;

import jakarta.transaction.Transactional;
import org.example.dacn_qllh_lms.dto.respone.ProfileRoleDTO;
import org.example.dacn_qllh_lms.entity.Profile;
import org.example.dacn_qllh_lms.entity.Role;
import org.example.dacn_qllh_lms.repository.Interface.ProfileRepository;
import org.example.dacn_qllh_lms.service.Interface.IProfileRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileRoleServiceImpl implements IProfileRoleService {
    @Autowired
    ProfileRepository profileRoleRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Page<ProfileRoleDTO> findAll(Pageable pageable) {
        Page<Profile> profiles = profileRoleRepository.findAllProfile(pageable);

        return profiles.map(profile -> {
            ProfileRoleDTO profileRoleDTO = modelMapper.map(profile, ProfileRoleDTO.class);

            // Assuming profile.getUser().getRoles() returns a Set<Role>
            Set<Role> roleNames = profile.getUser().getRoles();

            // Set role names in ProfileRoleDTO
            profileRoleDTO.setRoleName(roleNames);

            return profileRoleDTO;
        });
    }

    @Transactional
    public void updateProfileAndRole(Long profileId, String fullName, String phoneNumber, String address, Long userId,Long roleId) {
        // Cập nhật thông tin Profile
        profileRoleRepository.updateProfile(fullName, phoneNumber, address, profileId);

        // Cập nhật Role của User
        profileRoleRepository.updateUserRole(userId,roleId);
    }

}
