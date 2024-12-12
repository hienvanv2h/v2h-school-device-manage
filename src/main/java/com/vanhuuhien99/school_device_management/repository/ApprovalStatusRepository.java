package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.ApprovalStatusDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatusDefinition, Long> {
}
