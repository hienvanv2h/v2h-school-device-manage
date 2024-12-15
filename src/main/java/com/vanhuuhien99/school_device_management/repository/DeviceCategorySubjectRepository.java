package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.DeviceCategorySubject;
import com.vanhuuhien99.school_device_management.entity.DeviceCategorySubjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface DeviceCategorySubjectRepository extends JpaRepository<DeviceCategorySubject, DeviceCategorySubjectId> {

    @EntityGraph(attributePaths = {"deviceCategory", "subject"})
    @NonNull
    Page<DeviceCategorySubject> findAll(@NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"deviceCategory", "subject"})
    @Query("SELECT dcs FROM DeviceCategorySubject dcs " +
            "WHERE dcs.deviceCategory.categoryName = :categoryName"
    )
    Page<DeviceCategorySubject> findByCategoryName(String categoryName, Pageable pageable);

    @EntityGraph(attributePaths = {"deviceCategory", "subject"})
    @Query("SELECT dcs FROM DeviceCategorySubject dcs " +
            "WHERE dcs.subject.subjectName = :subjectName"
    )
    Page<DeviceCategorySubject> findBySubjectName(String subjectName, Pageable pageable);
}
