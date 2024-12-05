package com.vanhuuhien99.school_device_management.formmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm {
    private String keyword;
    private String filter;
}
