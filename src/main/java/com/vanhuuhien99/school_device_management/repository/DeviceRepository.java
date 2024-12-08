package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @EntityGraph(attributePaths = {"deviceCategory"})
    @NonNull
    Page<Device> findAll(@NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"deviceCategory"})
    Page<Device> findByDeviceNameContaining(String keyword, Pageable pageable);
}
