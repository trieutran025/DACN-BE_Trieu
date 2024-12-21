package org.example.dacn_qllh_lms.config.authentation;

import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)

public class SecurityConfig {
    @Resource
    UserDetailsService userDetailsService;

    JwtAuthenticationFilter jwtAuthenticationFilter;

    CustomLogoutHandler logoutHandler;
    @Resource
    CorsConfigurationSource corsConfigurationSource;
    private static final String[] PUBLIC_ENDPOINTS = {
            "/login/**",          // Đăng nhập
            "/home/**",           // Trang chủ
            "/user/register",     // Đăng ký tài khoản
            "/classes/list",      // Danh sách lớp học công khai
            "/schedule/public/**" // Lịch học công khai
    };

    private static final String[] ADMIN_ENDPOINTS = {
            "/api/user/**",           // Quản lý người dùng (CRUD)
            "/api/roles/**",           // Quản lý vai trò
            "/api/permissions/**",     // Quản lý quyền
            "/api/notifications/**",// Quản lý thông báo
            "/api/dashboard-stats",
            "/api/classes/"
//            "/api/reports/**"          // Báo cáo, thống kê hệ thống
    };

    private static final String[] TEACHER_ENDPOINTS = {
            "/api/classes/**",           // Quản lý lớp học của giáo viên
            "/api/lectures/**",          // Quản lý bài giảng
            "/api/assignments/**",       // Quản lý bài tập
            "/api/submissions/review/**",// Chấm bài nộp của học viên
            "/api/attendance/**",        // Điểm danh
            "/api/schedule/**"           // Quản lý lịch học
    };

    private static final String[] STUDENT_ENDPOINTS = {
            "/api/classes/my-classes/**",  // Xem danh sách lớp học đã đăng ký
            "/api/submissions/**",         // Nộp bài tập
            "/api/schedule/my-schedule/**",// Xem lịch học cá nhân
            "/api/attendance/status/**",   // Xem trạng thái điểm danh
            "/api/notifications/my/**"     // Xem thông báo cá nhân
    };



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors->cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(TEACHER_ENDPOINTS).hasRole("TEACHER")
                        .requestMatchers(STUDENT_ENDPOINTS).hasRole("STUDENT")
                        .anyRequest().authenticated()

                ).userDetailsService(userDetailsService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                            System.out.println("Current Authorities: " + authentication.getAuthorities());
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            System.out.println("Access Denied: " + accessDeniedException.getMessage());
                            System.out.println("User: " + authentication.getName() + " - Roles: " + authentication.getAuthorities());
                            try {
                                if (!authentication.getAuthorities().isEmpty()) {
                                    System.out.println("Authorities are present.");
                                } else {
                                    System.out.println("No authorities found. Please check role mapping in buildScope.");
                                }
                            } catch (Exception r) {
                                System.out.println("Error: " + r.getMessage());
                            }

                        })
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


}
