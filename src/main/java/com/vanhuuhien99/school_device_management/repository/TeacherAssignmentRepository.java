package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import com.vanhuuhien99.school_device_management.projection.TeacherAssignmentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment, Long> {

    @Query("SELECT ta.assignmentId AS assignmentId, " +
            "t.teacherId AS teacherId, t.fullName AS teacherName, " +
            "sc.classId AS classId, sc.className AS className, " +
            "s.subjectId AS subjectId, s.subjectName AS subjectName, " +
            "ta.semester AS semester, ta.description AS description, ta.createdAt AS createdAt, ta.updatedAt AS updatedAt " +
            "FROM TeacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject s "
    )
    Page<TeacherAssignmentProjection> findAllAssignments(Pageable pageable);

    @Query("SELECT ta.assignmentId AS assignmentId, " +
            "t.teacherId AS teacherId, t.fullName AS teacherName, " +
            "sc.classId AS classId, sc.className AS className, " +
            "s.subjectId AS subjectId, s.subjectName AS subjectName, " +
            "ta.semester AS semester, ta.description AS description, ta.createdAt AS createdAt, ta.updatedAt AS updatedAt " +
            "FROM TeacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject s " +
            "WHERE ta.assignmentId = :assignmentId "
    )
    Optional<TeacherAssignmentProjection> findTeacherAssignmentDetailsById(Long assignmentId);

    @Query("SELECT ta.assignmentId AS assignmentId, " +
            "t.teacherId AS teacherId, t.fullName AS teacherName, " +
            "sc.classId AS classId, sc.className AS className, " +
            "s.subjectId AS subjectId, s.subjectName AS subjectName, " +
            "ta.semester AS semester, ta.description AS description, ta.createdAt AS createdAt, ta.updatedAt AS updatedAt " +
            "FROM TeacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject s " +
            "WHERE t.fullName LIKE CONCAT('%', :keyword, '%') "
    )
    Page<TeacherAssignmentProjection> findByTeacherFullNameContaining(String keyword, Pageable pageable);

    @Query("SELECT ta.assignmentId AS assignmentId, " +
            "t.teacherId AS teacherId, t.fullName AS teacherName, " +
            "sc.classId AS classId, sc.className AS className, " +
            "s.subjectId AS subjectId, s.subjectName AS subjectName, " +
            "ta.semester AS semester, ta.description AS description, ta.createdAt AS createdAt, ta.updatedAt AS updatedAt " +
            "FROM TeacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject s " +
            "WHERE sc.className LIKE CONCAT('%', :keyword, '%') "
    )
    Page<TeacherAssignmentProjection> findByClassNameContaining(String keyword, Pageable pageable);

    @Query("SELECT ta.assignmentId AS assignmentId, " +
            "t.teacherId AS teacherId, t.fullName AS teacherName, " +
            "sc.classId AS classId, sc.className AS className, " +
            "s.subjectId AS subjectId, s.subjectName AS subjectName, " +
            "ta.semester AS semester, ta.description AS description, ta.createdAt AS createdAt, ta.updatedAt AS updatedAt " +
            "FROM TeacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject s " +
            "WHERE ta.semester LIKE CONCAT('%', :keyword, '%') "
    )
    Page<TeacherAssignmentProjection> findBySemesterContaining(String keyword, Pageable pageable);
}
