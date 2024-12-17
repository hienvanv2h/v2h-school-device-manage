package com.vanhuuhien99.school_device_management.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "scheduleId")
@Entity
@Table(name = "Schedules")
public class Schedule extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScheduleID")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssignmentID")
    private TeacherAssignment teacherAssignment;

    @Column(name = "DayOfWeek")
    private String dayOfWeek;

    @Column(name = "ScheduleDate", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "StartTime", nullable = false)
    private LocalTime startTime;

    @Column(name = "EndTime", nullable = false)
    private LocalTime endTime;

    @Column(name = "Location")
    private String location;
}
