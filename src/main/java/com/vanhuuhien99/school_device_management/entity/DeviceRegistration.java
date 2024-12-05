package com.vanhuuhien99.school_device_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "DeviceRegistrations")
public class DeviceRegistration extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RegistrationID")
    private Long registrationId;

    @ManyToOne
    @JoinColumn(name = "DeviceID")
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssignmentID")
    private TeacherAssignment teacherAssignment;

    @Column(name = "RegistrationStatus", length = 50, nullable = false)
    private String registrationStatus;

    @Column(name = "ApprovalStatus", length = 50, nullable = false)
    private String approvalStatus;

    @Column(name = "Description")
    private String description;
}
