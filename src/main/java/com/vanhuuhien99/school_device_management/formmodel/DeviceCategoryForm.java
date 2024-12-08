package com.vanhuuhien99.school_device_management.formmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class DeviceCategoryForm {

    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Category name must not exceed 100 characters")
    private String categoryName;

    private String description;

    @NotBlank(message = "Unit is required")
    @Size(max = 20, message = "Unit must not exceed 20 characters")
    private String unit;

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be a positive number")
    private Double unitPrice;
}
