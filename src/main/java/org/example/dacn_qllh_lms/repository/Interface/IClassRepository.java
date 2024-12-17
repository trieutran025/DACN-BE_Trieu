package org.example.dacn_qllh_lms.repository.Interface;

import org.example.dacn_qllh_lms.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassRepository extends JpaRepository<Class, Long> {
}
