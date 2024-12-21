package org.example.dacn_qllh_lms.service.Impl;

import org.example.dacn_qllh_lms.dto.request.ClassesRequestDTO;
import org.example.dacn_qllh_lms.entity.Classes;
import org.example.dacn_qllh_lms.repository.Interface.IClassRepository;
import org.example.dacn_qllh_lms.service.Interface.IClassService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements IClassService {
    @Autowired
    IClassRepository iClassRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Page<ClassesRequestDTO> findAll(Pageable pageable) {
        Page<Classes> classesPage = iClassRepository.findAllClass(pageable);

        return classesPage.map(classes->{
            ClassesRequestDTO classesRequestDTO = modelMapper.map(classes, ClassesRequestDTO.class);
            return classesRequestDTO;
        });
    }
}
