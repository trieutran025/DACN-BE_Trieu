package org.example.dacn_qllh_lms.repository.authentication;


import org.example.dacn_qllh_lms.entity.authentication.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface ITokenRepository extends JpaRepository<Token,Long> {
    @Query(value = """

            SELECT token.id, token.access_token, token.refresh_token, token.logged_out, token.users_id
                    FROM token
                    JOIN users ON token.users_id = users.users_id
                    WHERE users.users_id = ? AND token.logged_out = false
                    """, nativeQuery = true)
    List<Token> findAllAccessTokensByUser(Long userId);
    @Query(value = "SELECT id, access_token, refresh_token, logged_out, users_id FROM token WHERE access_token = :token", nativeQuery = true)
    Optional<Token> findByAccessToken(String token);

    @Query(value = "SELECT id, access_token, refresh_token, logged_out, users_id FROM token WHERE refresh_token = :token", nativeQuery = true)
    Optional<Token> findByRefreshToken(String token);

    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO token (access_token, refresh_token, logged_out, users_id) VALUES (:accessToken, :refreshToken, :loggedOut, :userId)",
            nativeQuery = true
    )
    void saveToken(
            @Param("accessToken") String accessToken,
            @Param("refreshToken") String refreshToken,
            @Param("loggedOut") boolean loggedOut,
            @Param("userId") Long userId
    );

    @Transactional
    @Modifying
    @Query(value = "UPDATE token SET logged_out = true WHERE id IN (:tokenIds)", nativeQuery = true)
    void updateTokensToLoggedOut(List<Long> tokenIds);

    @Modifying
    @Transactional
    @Query(value = "UPDATE token SET logged_out = true WHERE id = :id", nativeQuery = true)
    void logOutTokenById(Long id);
}
