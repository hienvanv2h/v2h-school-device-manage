package com.vanhuuhien99.school_device_management.specification;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import com.vanhuuhien99.school_device_management.entity.Teacher;
import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class TeacherAssignmentSpec {

    public static Specification<TeacherAssignment> containsTeacherFullName(String keyword) {
        return (root, query, cb) -> {
            if(!StringUtils.hasText(keyword)) {
                return cb.conjunction();
            }
            Join<TeacherAssignment, Teacher> teacherJoin = root.join("teacher", JoinType.INNER);
            return cb.like(cb.lower(teacherJoin.get("fullName")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<TeacherAssignment> containsSchoolClassName(String keyword) {
        return (root, query, cb) -> {
            if(!StringUtils.hasText(keyword)) {
                return cb.conjunction();
            }
            Join<TeacherAssignment, SchoolClass> schoolClassJoin = root.join("schoolClass", JoinType.INNER);
            return cb.like(cb.lower(schoolClassJoin.get("className")),"%" + keyword.toLowerCase() + "%"
            );
        };
    }

    public static Specification<TeacherAssignment> containsSemesterName(String keyword) {
        return (root, query, cb) -> {
            if(!StringUtils.hasText(keyword)) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("semester")),"%" + keyword.toLowerCase() + "%");
        };
    }
}
