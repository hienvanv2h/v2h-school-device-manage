package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.formmodel.ScheduleForm;
import com.vanhuuhien99.school_device_management.projection.ScheduleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {

    Page<ScheduleProjection> getFilteredSchedules(String keyword, String filter, Pageable pageable);

    Page<ScheduleProjection> getAllSchedules(Pageable pageable);

    ScheduleProjection getScheduleById(Long scheduleId);

    List<ScheduleProjection> getScheduleByTeacherAssignmentId(Long assignmentId);

    Page<ScheduleProjection> searchSchedulesByTeacherNameContaining(String keyword, Pageable pageable);

    Page<ScheduleProjection> searchSchedulesByClassNameContaining(String keyword, Pageable pageable);

    Page<ScheduleProjection> searchSchedulesBySubjectNameContaining(String keyword, Pageable pageable);

    void createNewSchedule(ScheduleForm form);

    void updateSchedule(ScheduleForm form, Long scheduleId);

    void deleteSchedule(Long scheduleId);
}
