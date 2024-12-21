package org.example.dacn_qllh_lms.repository.Interface;


import org.example.dacn_qllh_lms.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
    @Query(
            value = "SELECT p.profile_id, p.full_name, p.phone_number, p.address, p.created_at, p.updated_at, p.users_id " +
                    "FROM profiles p " +
                    "JOIN users u ON p.users_id = u.users_id " +
                    "JOIN users_roles ur ON u.users_id = ur.user_id " +
                    "JOIN roles r ON ur.role_id = r.role_id " +
                    "WHERE r.role_name IN ('instructor', 'student')",
            countQuery = "SELECT COUNT(*) " +
                    "FROM profiles p " +
                    "JOIN users u ON p.users_id = u.users_id " +
                    "JOIN users_roles ur ON u.users_id = ur.user_id " +
                    "JOIN roles r ON ur.role_id = r.role_id " +
                    "WHERE r.role_name IN ('instructor', 'student')",
            nativeQuery = true
    )
    Page<Profile> findAllProfile(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE profiles p SET p.full_name = :fullName, p.phone_number = :phoneNumber, p.address = :address WHERE p.profile_id = :profileId", nativeQuery = true)
    void updateProfile(@Param("fullName") String fullName,
                       @Param("phoneNumber") String phoneNumber,
                       @Param("address") String address,
                       @Param("profileId") Long profileId);
    @Modifying
    @Query(value = "UPDATE users_roles ur SET ur.role_id = :roleId WHERE ur.user_id = :userId AND ur.user_id IN (SELECT p.profile_id FROM profiles p WHERE p.profile_id = ur.user_id)", nativeQuery = true)
    void updateUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);



}
