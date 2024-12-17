package org.example.dacn_qllh_lms.repository.Interface;

import org.example.dacn_qllh_lms.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILectureRepository extends JpaRepository<Lecture, Long> {
}

