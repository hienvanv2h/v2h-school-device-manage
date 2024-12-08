package com.vanhuuhien99.school_device_management.formmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubjectForm {

    @NotBlank(message = "Subject name is required")
    @Size(max = 100, message = "Subject name must not exceed 100 characters")
    private String subjectName;
}
