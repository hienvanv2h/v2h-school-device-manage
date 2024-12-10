package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.Schedule;
import com.vanhuuhien99.school_device_management.projection.ScheduleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s.scheduleId AS scheduleId, s.dayOfWeek AS dayOfWeek, " +
            "s.scheduleDate AS scheduleDate, s.startTime AS startTime, s.endTime AS endTime, s.location AS location, " +
            "ta.assignmentId AS assignmentId, " +
            "t.fullName AS teacherName, sc.className AS className, sub.subjectName AS subjectName, " +
            "s.createdAt AS createdAt, s.updatedAt AS updatedAt " +
            "FROM Schedule s " +
            "JOIN s.teacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject sub "
    )
    Page<ScheduleProjection> findAllSchedules(Pageable pageable);

    @Query("SELECT s.scheduleId AS scheduleId, s.dayOfWeek AS dayOfWeek, " +
            "s.scheduleDate AS scheduleDate, s.startTime AS startTime, s.endTime AS endTime, s.location AS location, " +
            "ta.assignmentId AS assignmentId, " +
            "t.fullName AS teacherName, sc.className AS className, sub.subjectName AS subjectName, " +
            "s.createdAt AS createdAt, s.updatedAt AS updatedAt " +
            "FROM Schedule s " +
            "JOIN s.teacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject sub " +
            "WHERE s.scheduleId = :scheduleId "
    )
    Optional<ScheduleProjection> findScheduleById(Long scheduleId);

    @Query("SELECT s.scheduleId AS scheduleId, s.dayOfWeek AS dayOfWeek, " +
            "s.scheduleDate AS scheduleDate, s.startTime AS startTime, s.endTime AS endTime, s.location AS location, " +
            "ta.assignmentId AS assignmentId, " +
            "t.fullName AS teacherName, sc.className AS className, sub.subjectName AS subjectName, " +
            "s.createdAt AS createdAt, s.updatedAt AS updatedAt " +
            "FROM Schedule s " +
            "JOIN s.teacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject sub " +
            "WHERE t.fullName LIKE CONCAT('%', :keyword, '%') "
    )
    Page<ScheduleProjection> findByTeacherFullNameContaining(String keyword, Pageable pageable);

    @Query("SELECT s.scheduleId AS scheduleId, s.dayOfWeek AS dayOfWeek, " +
            "s.scheduleDate AS scheduleDate, s.startTime AS startTime, s.endTime AS endTime, s.location AS location, " +
            "ta.assignmentId AS assignmentId, " +
            "t.fullName AS teacherName, sc.className AS className, sub.subjectName AS subjectName, " +
            "s.createdAt AS createdAt, s.updatedAt AS updatedAt " +
            "FROM Schedule s " +
            "JOIN s.teacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject sub " +
            "WHERE sc.className LIKE CONCAT('%', :keyword, '%') "
    )
    Page<ScheduleProjection> findByClassNameContaining(String keyword, Pageable pageable);

    @Query("SELECT s.scheduleId AS scheduleId, s.dayOfWeek AS dayOfWeek, " +
            "s.scheduleDate AS scheduleDate, s.startTime AS startTime, s.endTime AS endTime, s.location AS location, " +
            "ta.assignmentId AS assignmentId, " +
            "t.fullName AS teacherName, sc.className AS className, sub.subjectName AS subjectName, " +
            "s.createdAt AS createdAt, s.updatedAt AS updatedAt " +
            "FROM Schedule s " +
            "JOIN s.teacherAssignment ta " +
            "JOIN ta.teacher t " +
            "JOIN ta.schoolClass sc " +
            "JOIN ta.subject sub " +
            "WHERE sub.subjectName LIKE CONCAT('%', :keyword, '%') "
    )
    Page<ScheduleProjection> findBySubjectNameContaining(String keyword, Pageable pageable);
}
