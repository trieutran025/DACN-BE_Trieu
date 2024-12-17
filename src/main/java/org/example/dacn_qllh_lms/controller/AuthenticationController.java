package org.example.dacn_qllh_lms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.example.dacn_qllh_lms.dto.authentication.AuthenticationRequest;
import org.example.dacn_qllh_lms.exception.authentication.AccountNotFoundException;
import org.example.dacn_qllh_lms.exception.authentication.InvalidPasswordException;
import org.example.dacn_qllh_lms.repository.authentication.AuthenticationResponse;
import org.example.dacn_qllh_lms.service.Impl.authentication.AuthenticationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    AuthenticationServiceImpl authService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        try {
            AuthenticationResponse response = authService.authenticate(request);
            if (response != null && response.isAuthenticated()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(AuthenticationResponse.builder()
                                .message("Mật khẩu không đúng.")
                                .isAuthenticated(false)
                                .build());
            }
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(AuthenticationResponse.builder()
                            .message("Mật khẩu không đúng.")
                            .isAuthenticated(false)
                            .build());
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(AuthenticationResponse.builder()
                            .message("Tài khoản không tồn tại.")
                            .isAuthenticated(false)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AuthenticationResponse.builder()
                            .message(e.getMessage())
                            .isAuthenticated(false)
                            .build());
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }

    // Exception handler for any other unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi không xác định: " + e.getMessage());
    }
}   
