package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Page<Subject> findBySubjectNameContaining(String keyword, Pageable pageable);
}
