package org.example.dacn_qllh_lms.service.Impl;

import org.example.dacn_qllh_lms.repository.Interface.IUserRepository;
import org.example.dacn_qllh_lms.service.Interface.IAdminService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminServiceImpl implements IAdminService {
    IUserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public void createUserWithRole(String username, String email, String password, Long roleId) {
        String hashedPassword = passwordEncoder.encode(password);
        userRepository.createUserWithRole(username, email, hashedPassword, roleId);
    }
}
