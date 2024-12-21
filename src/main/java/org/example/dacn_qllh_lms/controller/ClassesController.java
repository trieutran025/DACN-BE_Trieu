package org.example.dacn_qllh_lms.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dacn_qllh_lms.dto.request.ClassesRequestDTO;
import org.example.dacn_qllh_lms.service.Interface.IClassService;
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
@RequestMapping("/api/classes/")
public class ClassesController {
    @Autowired
    IClassService iClassService;
    @GetMapping("")
    public ResponseEntity<Page<ClassesRequestDTO>> getAllClasses(
            @RequestParam("page")Optional<Integer> page,
            @RequestParam(value = "size",defaultValue = "5") int size){
        if(page.orElse(0)<0||size<=0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(page.orElse(0),size);
        Page<ClassesRequestDTO> classesRequestDTOS =iClassService.findAll(pageable);
        if(classesRequestDTOS.getContent().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(classesRequestDTOS,HttpStatus.OK);
    }
}
