package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment, Long>, JpaSpecificationExecutor<TeacherAssignment> {

    @EntityGraph(attributePaths = {"teacher", "schoolClass", "subject"})
    @NonNull
    Page<TeacherAssignment> findAll(Specification<TeacherAssignment> spec, @NonNull Pageable pageable);
}
