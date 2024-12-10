package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    Page<Teacher> findByFullNameContaining(String keyword, Pageable pageable);

    Page<Teacher> findByEmailContaining(String keyword, Pageable pageable);

    Page<Teacher> findByPhoneNumberContaining(String keyword, Pageable pageable);

    <T> List<T> findBy(Class<T> classType);
}
