package com.vanhuuhien99.school_device_management.specification;

import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import com.vanhuuhien99.school_device_management.entity.Teacher;
import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class DeviceRegistrationSpec {

    public static Specification<DeviceRegistration> containsTeacherFullName(String keyword) {
        return (root, query, cb) -> {
            if(!StringUtils.hasText(keyword)) {
                return cb.conjunction();
            }
            Join<DeviceRegistration, TeacherAssignment> teacherAssignmentJoin = root.join("teacherAssignment", JoinType.INNER);
            Join<TeacherAssignment, Teacher> teacherJoin = teacherAssignmentJoin.join("teacher", JoinType.INNER);
            return cb.like(
                    cb.lower(teacherJoin.get("fullName")),
                    "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<DeviceRegistration> containsDeviceName(String keyword) {
        return (root, query, cb) -> {
            if(!StringUtils.hasText(keyword)) {
                return cb.conjunction();
            }
            Join<DeviceRegistration, Device> deviceJoin = root.join("device", JoinType.INNER);
            return cb.like(
                    cb.lower(deviceJoin.get("deviceName")),
                    "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<DeviceRegistration> hasTeacherPhoneNumber(String phoneNumber) {
        return (root, query, cb) -> {
            if(!StringUtils.hasText(phoneNumber)) {
                return cb.conjunction();
            }
            Join<DeviceRegistration, TeacherAssignment> teacherAssignmentJoin = root.join("teacherAssignment", JoinType.INNER);
            Join<TeacherAssignment, Teacher> teacherJoin = teacherAssignmentJoin.join("teacher", JoinType.INNER);
            return cb.equal(teacherJoin.get("phoneNumber"), phoneNumber);
        };
    }

    public static Specification<DeviceRegistration> hasApprovalStatus(String approvalStatus) {
        return (root, query, cb) -> {
            if(!StringUtils.hasText(approvalStatus)) {
                return cb.conjunction();
            }
            return cb.equal(root.get("approvalStatus"), approvalStatus);
        };
    }
}
