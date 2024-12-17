package com.vanhuuhien99.school_device_management.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "assignmentId")
@Entity
@Table(name = "TeacherAssignments")
public class TeacherAssignment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssignmentID")
    private Long assignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TeacherID", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClassID", nullable = false)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectID", nullable = false)
    private Subject subject;

    @Column(name = "Semester", length = 20, nullable = false)
    private String semester;

    @Column(name = "Description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacherAssignment")
    private List<Schedule> schedules;
}
