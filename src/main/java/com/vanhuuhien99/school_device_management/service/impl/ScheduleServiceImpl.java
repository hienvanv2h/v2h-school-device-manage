package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.Schedule;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.ScheduleForm;
import com.vanhuuhien99.school_device_management.projection.ScheduleProjection;
import com.vanhuuhien99.school_device_management.repository.ScheduleRepository;
import com.vanhuuhien99.school_device_management.repository.TeacherAssignmentRepository;
import com.vanhuuhien99.school_device_management.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final TeacherAssignmentRepository teacherAssignmentRepository;


    @Override
    public Page<ScheduleProjection> getAllSchedules(Pageable pageable) {
        return scheduleRepository.findAllSchedules(pageable);
    }

    @Override
    public ScheduleProjection getScheduleById(Long scheduleId) {
        return scheduleRepository.findScheduleById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find schedule with id: " + scheduleId));
    }

    @Override
    public Page<ScheduleProjection> searchSchedulesByTeacherNameContaining(String keyword, Pageable pageable) {
        return scheduleRepository.findByTeacherFullNameContaining(keyword, pageable);
    }

    @Override
    public Page<ScheduleProjection> searchSchedulesByClassNameContaining(String keyword, Pageable pageable) {
        return scheduleRepository.findByClassNameContaining(keyword, pageable);
    }

    @Override
    public Page<ScheduleProjection> searchSchedulesBySubjectNameContaining(String keyword, Pageable pageable) {
        return scheduleRepository.findBySubjectNameContaining(keyword, pageable);
    }

    @Override
    public void createNewSchedule(ScheduleForm form) {
        var existingTeacherAssignment = teacherAssignmentRepository.findById(form.getTeacherAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find teacher assignment with id: " + form.getTeacherAssignmentId()));
        Schedule newSchedule = Schedule.builder()
                .teacherAssignment(existingTeacherAssignment)
                .dayOfWeek(form.getDayOfWeek())
                .scheduleDate(form.getScheduleDate())
                .startTime(form.getStartTime())
                .endTime(form.getEndTime())
                .location(form.getLocation())
                .build();
        scheduleRepository.save(newSchedule);
    }

    @Override
    public void updateSchedule(ScheduleForm form, Long scheduleId) {
        Schedule existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find schedule with id: " + scheduleId));

        var existingTeacherAssignment = teacherAssignmentRepository.findById(form.getTeacherAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find teacher assignment with id: " + form.getTeacherAssignmentId()));

        existingSchedule.setTeacherAssignment(existingTeacherAssignment);
        existingSchedule.setDayOfWeek(form.getDayOfWeek());
        existingSchedule.setScheduleDate(form.getScheduleDate());
        existingSchedule.setStartTime(form.getStartTime());
        existingSchedule.setEndTime(form.getEndTime());
        existingSchedule.setLocation(form.getLocation());
        scheduleRepository.save(existingSchedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        Schedule existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find schedule with id: " + scheduleId));
        scheduleRepository.delete(existingSchedule);
    }
}
