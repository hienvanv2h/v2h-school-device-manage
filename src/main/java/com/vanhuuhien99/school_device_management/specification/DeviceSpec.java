package com.vanhuuhien99.school_device_management.specification;

import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.entity.DeviceCategorySubject;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class DeviceSpec {

    public static Specification<Device> containsDeviceName(String keyword) {
        return (root, query, cb) -> {
            if(!StringUtils.hasText(keyword)) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("deviceName")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<Device> hasSubjectName(String subjectName) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(subjectName)) {
                return cb.conjunction();
            }
            Join<Device, DeviceCategory> deviceCategoryJoin = root.join("deviceCategory", JoinType.INNER);
            Join<DeviceCategory, DeviceCategorySubject> deviceCategorySubjectJoin = deviceCategoryJoin.join("deviceCategorySubjects", JoinType.INNER);
            return cb.equal(deviceCategorySubjectJoin.get("subject").get("subjectName"), subjectName);
        };
    }
}
