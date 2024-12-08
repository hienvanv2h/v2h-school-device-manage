package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.Subject;
import com.vanhuuhien99.school_device_management.formmodel.SubjectForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectService {

    Page<Subject> getAllSubjects(Pageable pageable);

    Page<Subject> searchBySubjectNameContaining(String keyword, Pageable pageable);

    Subject getSubjectById(Long subjectId);

    void createNewSubject(SubjectForm form);

    void updateSubject(SubjectForm form, Long subjectId);

    void deleteSubject(Long subjectId);
}
