package org.example.dacn_qllh_lms.repository.Interface;

import org.example.dacn_qllh_lms.entity.Classes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassRepository extends JpaRepository<Classes, Long> {

    @Query(
            value = "SELECT c.class_id, c.class_name, c.class_code, c.description, DATE(c.start_date) AS start_date,c.created_at,c.end_date " +
                    "FROM classes c ",
            countQuery = "SELECT COUNT(*) " +
                    "FROM classes c " ,nativeQuery = true
    )
    Page<Classes> findAllClass(Pageable pageable);

}
