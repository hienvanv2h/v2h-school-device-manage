package com.vanhuuhien99.school_device_management.formmodel;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeviceCategorySubjectForm {

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;
}
