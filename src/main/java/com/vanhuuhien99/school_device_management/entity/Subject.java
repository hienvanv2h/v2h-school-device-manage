package com.vanhuuhien99.school_device_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "Subjects")
public class Subject extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubjectID")
    private Long subjectId;

    @Column(name = "SubjectName", nullable = false, length = 100)
    private String subjectName;
}
