package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.dto.Result;
import com.vanhuuhien99.school_device_management.entity.Semester;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.SemesterForm;
import com.vanhuuhien99.school_device_management.repository.SemesterRepository;
import com.vanhuuhien99.school_device_management.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    @Override
    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    @Override
    public Page<Semester> getAllSemesters(Pageable pageable) {
        return semesterRepository.findAll(pageable);
    }

    @Override
    public Page<Semester> getFilteredSemesters(String keyword, String filter, PageRequest pageable) {
        if(keyword == null || keyword.isEmpty() || filter == null || filter.isEmpty()) {
            return getAllSemesters(pageable);
        } else {
            // Các giá trị khớp xem trong lớp ColumnMapping
            if(filter.equalsIgnoreCase("semesterName")) {
                return searchBySemesterNameContaining(keyword, pageable);
            } else {
                return getAllSemesters(pageable);
            }
        }
    }

    @Override
    public Page<Semester> searchBySemesterNameContaining(String keyword, Pageable pageable) {
        return semesterRepository.findBySemesterNameContaining(keyword, pageable);
    }

    @Override
    @Transactional
    public Result<Semester> createNewSemester(SemesterForm form) {
        var existingSemester = semesterRepository.findBySemesterName(form.getSemesterName());
        if(existingSemester.isPresent()) {
            return Result.failure("Semester name already exists");
        }
        var newSemester = Semester.builder()
                .semesterName(form.getSemesterName())
                .startDate(form.getStartDate())
                .endDate(form.getEndDate())
                .build();
        var savedSemester = semesterRepository.save(newSemester);
        return Result.success(savedSemester);
    }

    @Override
    @Transactional
    public Semester getSemesterById(Long semesterId) {
        return semesterRepository.findById(semesterId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find semester with id: " + semesterId));
    }

    @Override
    @Transactional
    public Result<Semester> updateSemester(SemesterForm form, Long semesterId) {
        var existingSemester = semesterRepository.findBySemesterName(form.getSemesterName());
        if(existingSemester.isPresent()) {
            return Result.failure("Semester name already exists");
        }
        var semester = getSemesterById(semesterId);
        semester.setSemesterName(form.getSemesterName());
        semester.setStartDate(form.getStartDate());
        semester.setEndDate(form.getEndDate());
        var updatedSemester = semesterRepository.save(semester);
        return Result.success(updatedSemester);
    }

    @Override
    @Transactional
    public void deleteSemester(Long semesterId) {
        var existingSemester = getSemesterById(semesterId);
        semesterRepository.delete(existingSemester);
    }
}
