package com.vanhuuhien99.school_device_management.formmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class UserFormUpdate {

    @Size(min = 5, max = 50, message = "Password must be at least 5 characters and not exceed 50 characters")
    private String password;

    private String confirmPassword;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    private Long roleId;

    private Boolean isActive;
}
