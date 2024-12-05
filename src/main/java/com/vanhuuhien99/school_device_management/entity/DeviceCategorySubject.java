package com.vanhuuhien99.school_device_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DeviceCategoriesSubjects")
public class DeviceCategorySubject extends BaseEntity {

    // Composite key
    @EmbeddedId
    private DeviceCategorySubjectId id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "CategoryID")
    private DeviceCategory deviceCategory;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "SubjectID")
    private Subject subject;

    public DeviceCategorySubject(DeviceCategory deviceCategory, Subject subject) {
        this.id = new DeviceCategorySubjectId(deviceCategory.getCategoryId(), subject.getSubjectId());
        this.deviceCategory = deviceCategory;
        this.subject = subject;
    }
}
