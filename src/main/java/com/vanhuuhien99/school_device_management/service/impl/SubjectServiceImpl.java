package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.Subject;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.SubjectForm;
import com.vanhuuhien99.school_device_management.repository.SubjectRepository;
import com.vanhuuhien99.school_device_management.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public Page<Subject> getAllSubjects(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Page<Subject> searchBySubjectNameContaining(String keyword, Pageable pageable) {
        return subjectRepository.findBySubjectNameContaining(keyword, pageable);
    }

    @Override
    public Subject getSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find subject with id: " + subjectId));
    }

    @Override
    public void createNewSubject(SubjectForm form) {
        var newSubject = Subject.builder()
                .subjectName(form.getSubjectName())
                .build();
        subjectRepository.save(newSubject);
    }

    @Override
    public void updateSubject(SubjectForm form, Long subjectId) {
        var existingSubject = getSubjectById(subjectId);
        existingSubject.setSubjectName(form.getSubjectName());
        subjectRepository.save(existingSubject);
    }

    @Override
    public void deleteSubject(Long subjectId) {
        var existingSubject = getSubjectById(subjectId);
        subjectRepository.delete(existingSubject);
    }
}
