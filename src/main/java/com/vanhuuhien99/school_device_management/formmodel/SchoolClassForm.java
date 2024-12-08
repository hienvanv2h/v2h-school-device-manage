package com.vanhuuhien99.school_device_management.formmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SchoolClassForm {

    @NotBlank(message = "Class name is required")
    @Size(max = 20, message = "Class name must not exceed 20 characters")
    private String className;
}
