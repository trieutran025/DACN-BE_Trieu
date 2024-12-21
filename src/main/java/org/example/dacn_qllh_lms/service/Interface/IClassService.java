package org.example.dacn_qllh_lms.service.Interface;

import org.example.dacn_qllh_lms.dto.request.ClassesRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface IClassService {
    Page<ClassesRequestDTO> findAll(Pageable pageable);
}
