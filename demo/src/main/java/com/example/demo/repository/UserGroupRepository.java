package com.example.demo.repository;

import com.example.demo.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    List<UserGroup> findAllByDeletedAtNull();
    Optional<UserGroup> findByIdAndDeletedAtNull(Long id);
}
