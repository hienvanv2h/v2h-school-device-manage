package com.vanhuuhien99.school_device_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Semesters")
public class Semester {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SemesterID")
    private Long semesterId;

    @Column(name = "SemesterName", length = 100, nullable = false, unique = true)
    private String semesterName;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;
}
