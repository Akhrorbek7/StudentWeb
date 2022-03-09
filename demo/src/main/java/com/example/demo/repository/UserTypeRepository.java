package com.example.demo.repository;

import com.example.demo.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {

    Optional<UserType> findByIdAndDeletedAtIsNull(Long id);

    List<UserType> findAllByDeletedAtIsNull();
}
