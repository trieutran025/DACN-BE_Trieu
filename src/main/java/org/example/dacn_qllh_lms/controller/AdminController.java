package org.example.dacn_qllh_lms.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dacn_qllh_lms.dto.request.UserRoleDTO;
import org.example.dacn_qllh_lms.dto.respone.ProfileRoleDTO;
import org.example.dacn_qllh_lms.service.Impl.ProfileRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("*")
@RequestMapping("/api/user/")
public class AdminController {

    @Autowired
    ProfileRoleServiceImpl profileRoleService;

    @GetMapping("")
    public ResponseEntity<Page<ProfileRoleDTO>> getProfilesWithRoles(
                @RequestParam("page") Optional<Integer> page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        if (page.orElse(0) < 0 || size <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), size);
        Page<ProfileRoleDTO> profileRoleDTOS = profileRoleService.findAll(pageable);

        if (profileRoleDTOS.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(profileRoleDTOS, HttpStatus.OK);
    }
        @PutMapping("/{id}")
        public void updateProfileAndRole(@RequestBody UserRoleDTO request,@PathVariable Long id) {
            profileRoleService.updateProfileAndRole(id, request.getFullName(),
                    request.getPhoneNumber(), request.getAddress(),
                    request.getUserId(),request.getRoleId());
        }


}
