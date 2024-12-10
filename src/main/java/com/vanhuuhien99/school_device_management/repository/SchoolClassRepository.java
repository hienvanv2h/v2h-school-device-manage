package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    Page<SchoolClass> findByClassNameContaining(@Param("keyword") String keyword, Pageable pageable);

    <T> List<T> findBy(Class<T> classType);
}
