package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    @EntityGraph(attributePaths = {"role"})
    Optional<User> findByUsername(String username);
}
