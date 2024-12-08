package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.dto.TeacherAssignmentDto;
import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment, Long> {

    @Query("SELECT new com.vanhuuhien99.school_device_management.dto.TeacherAssignmentDto(ta.assignmentId, t.fullName, sc.className, s.subjectName, ta.semester, ta.description) " +
            "FROM TeacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject s " +
            "WHERE t.fullName LIKE CONCAT('%', :keyword, '%') "
    )
    Page<TeacherAssignmentDto> findByTeacherFullNameContaining(String keyword, Pageable pageable);

    @Query("SELECT new com.vanhuuhien99.school_device_management.dto.TeacherAssignmentDto(ta.assignmentId, t.fullName, sc.className, s.subjectName, ta.semester, ta.description) " +
            "FROM TeacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject s " +
            "WHERE sc.className LIKE CONCAT('%', :keyword, '%') "
    )
    Page<TeacherAssignmentDto> findByClassNameContaining(String keyword, Pageable pageable);

    Page<TeacherAssignment> findBySemesterContaining(String keyword, Pageable pageable);
}
