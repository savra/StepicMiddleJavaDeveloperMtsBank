package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.User;

import java.util.Optional;

public interface UserService {
    User getCurrentUser();

    Optional<User> test2();
}
