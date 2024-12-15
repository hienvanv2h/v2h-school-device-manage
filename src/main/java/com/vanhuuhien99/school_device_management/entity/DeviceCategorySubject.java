package com.vanhuuhien99.school_device_management.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DeviceCategorySubjects")
public class DeviceCategorySubject extends BaseEntity {

    // Composite key
    @EmbeddedId
    private DeviceCategorySubjectId id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "CategoryID")
    @JsonManagedReference
    private DeviceCategory deviceCategory;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "SubjectID")
    @JsonManagedReference
    private Subject subject;

    public DeviceCategorySubject(DeviceCategory deviceCategory, Subject subject) {
        this.id = new DeviceCategorySubjectId(deviceCategory.getCategoryId(), subject.getSubjectId());
        this.deviceCategory = deviceCategory;
        this.subject = subject;
    }
}
