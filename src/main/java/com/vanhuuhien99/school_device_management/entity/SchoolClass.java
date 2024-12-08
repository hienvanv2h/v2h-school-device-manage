package com.vanhuuhien99.school_device_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "SchoolClasses")
public class SchoolClass extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClassID")
    private Long classId;

    @Column(name = "ClassName", length = 20, nullable = false)
    private String className;
}
