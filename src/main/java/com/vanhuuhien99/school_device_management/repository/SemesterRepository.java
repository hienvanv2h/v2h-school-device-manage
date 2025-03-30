package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.Semester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SemesterRepository extends JpaRepository<Semester, Long> {

    Optional<Semester> findBySemesterName(String semesterName);

    Page<Semester> findBySemesterNameContaining(String keyword, Pageable pageable);
}
