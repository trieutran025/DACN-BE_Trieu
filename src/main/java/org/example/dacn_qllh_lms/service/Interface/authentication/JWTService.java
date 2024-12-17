package org.example.dacn_qllh_lms.service.Interface.authentication;


import io.jsonwebtoken.Claims;
import org.example.dacn_qllh_lms.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public interface JWTService {
    String extractUsername(String token);
    boolean isValid(String token, UserDetails userDetails);
    boolean isValidRefreshToken(String token, User user);
    <T> T extractClaim(String token, Function<Claims,T> resolver);
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    String[] getRolesFromToken(String token);
}
