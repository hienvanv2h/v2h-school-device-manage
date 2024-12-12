package com.vanhuuhien99.school_device_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ApprovalStatusDefinitions")
public class ApprovalStatusDefinition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private Long statusId;

    @Column(name = "ApprovalStatus", length = 50, nullable = false, unique = true)
    private String approvalStatus;

    @Column(name = "Description")
    private String description;
}
