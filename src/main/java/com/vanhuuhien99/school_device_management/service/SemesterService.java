package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.dto.Result;
import com.vanhuuhien99.school_device_management.entity.Semester;
import com.vanhuuhien99.school_device_management.formmodel.SemesterForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SemesterService {

    List<Semester> getAllSemesters();

    Page<Semester> getAllSemesters(Pageable pageable);

    Page<Semester> getFilteredSemesters(String keyword, String filter, PageRequest pageRequest);

    Page<Semester> searchBySemesterNameContaining(String keyword, Pageable pageable);

    Result<Semester> createNewSemester(SemesterForm form);

    Semester getSemesterById(Long semesterId);

    Result<Semester> updateSemester(SemesterForm form, Long semesterId);

    void deleteSemester(Long semesterId);
}
