package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    List<User> findAllByDeletedAtNull();
    Optional<User> findByUserIdAndPhoneAndDeletedAtNull(Long userId, String phone);
    Optional<User> findByPhoneAndDeletedAtNull(String phone);
}
