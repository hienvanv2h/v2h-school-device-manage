package com.vanhuuhien99.school_device_management.entity;

import com.vanhuuhien99.school_device_management.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "Teachers")
public class Teacher extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TeacherID")
    private Long teacherId;

    @Column(name = "FullName", nullable = false, length = 100)
    private String fullName;

    @Column(name = "Email", length = 254)
    private String email;

    @Column(name = "PhoneNumber", length = 20, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    // @Convert(converter = GenderConverter.class)
    @Column(name = "Gender", length = 10)
    private Gender gender;
}
