package org.example.dacn_qllh_lms.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.example.dacn_qllh_lms.dto.request.UserRequestDTO;
import org.example.dacn_qllh_lms.service.Impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("*")
public class UserController {
    UserServiceImpl userService;
    @PostMapping("/user/register")
    public ResponseEntity<String> createStudentAccount(@RequestBody UserRequestDTO userRequestDTO){
        String result = userService.createAccountStudent(userRequestDTO);
        if(result.equals("Student account created successfully")){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(result,HttpStatus.CONFLICT);
        }
    }
}
