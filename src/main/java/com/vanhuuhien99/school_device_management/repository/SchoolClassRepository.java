package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    @Query(value = "SELECT * FROM SchoolClasses WHERE ClassName LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    Page<SchoolClass> searchByClassName(@Param("keyword") String keyword, Pageable pageable);
}
