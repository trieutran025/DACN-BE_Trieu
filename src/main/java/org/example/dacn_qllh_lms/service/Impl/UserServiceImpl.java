package org.example.dacn_qllh_lms.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.dacn_qllh_lms.dto.request.ChangePasswordRequest;
import org.example.dacn_qllh_lms.dto.request.UserRequestDTO;
import org.example.dacn_qllh_lms.dto.respone.ChangePasswordResponse;
import org.example.dacn_qllh_lms.entity.User;
import org.example.dacn_qllh_lms.repository.Interface.IUserRepository;
import org.example.dacn_qllh_lms.service.Interface.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    IUserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Transactional
    public String createAccountStudent(UserRequestDTO userRequestDTO) {
        // Step_1: Check if account exists
        Optional<User> existingAccount = userRepository.findByUsername(userRequestDTO.getUsername());
        if (existingAccount.isPresent()) {
            return "Account with username: " + userRequestDTO.getUsername() + " already exists";
        }
        // Step_3: Encrypt password
        String encryptedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
        // Step_4: Create account
        int status = userRepository.createStudentAccount(userRequestDTO.getUsername(), userRequestDTO.getEmail(), encryptedPassword);
        if (status == 1) {
            return "Student account created successfully";
        } else {
            return "Student account created failed";
        }
    }
    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Access roles to initialize them within the transaction

        user.getRoles().size();
        return user;
    }
}
