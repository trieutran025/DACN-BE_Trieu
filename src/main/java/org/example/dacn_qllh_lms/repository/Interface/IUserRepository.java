package org.example.dacn_qllh_lms.repository.Interface;

import jakarta.transaction.Transactional;
import org.example.dacn_qllh_lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO USERS(username,email,password)VALUES(:username,:email,:password)",nativeQuery = true)
    Integer createStudentAccount(@Param("username") String username, @Param("email") String email,@Param("password") String password);
    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO USERS(username, email, password) 
    VALUES (:username, :email, :password);

    INSERT INTO USER_ROLE(user_id, role_id) 
    VALUES (LAST_INSERT_ID(), :roleId);
    """, nativeQuery = true)
    void createUserWithRole(@Param("username") String username,
                            @Param("email") String email,
                            @Param("password") String password,
                            @Param("roleId") Long roleId);
}