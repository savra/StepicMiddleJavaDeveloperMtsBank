package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.repository;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
