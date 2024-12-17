package org.example.dacn_qllh_lms.service.Interface;


public interface IAdminService {
    void createUserWithRole(String username, String email, String password, Long roleId);
}
