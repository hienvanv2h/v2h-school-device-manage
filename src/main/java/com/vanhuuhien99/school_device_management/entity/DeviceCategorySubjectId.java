package com.vanhuuhien99.school_device_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DeviceCategorySubjectId implements Serializable {

    @Column(name = "CategoryID")
    private Long categoryId;

    @Column(name = "SubjectID")
    private Long subjectId;
}
