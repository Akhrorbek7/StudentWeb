package com.example.demo.repository;

import com.example.demo.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByDeletedAtIsNull();
    Optional<Group> findByIdAndDeletedAtNull(Long id);
}
