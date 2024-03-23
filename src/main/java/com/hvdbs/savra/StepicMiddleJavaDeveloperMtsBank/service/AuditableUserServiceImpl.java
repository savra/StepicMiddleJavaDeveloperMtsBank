package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class AuditableUserServiceImpl implements UserService {
    private final UserService userService;

    public AuditableUserServiceImpl(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getCurrentUser() {
        return audit(userService::getCurrentUser);
    }

    @Override
    public Optional<User> test2() {
        return audit(userService::test2);
    }

    private <T> T audit(Supplier<T> target) {
        System.out.println("Был вызван метод аудирования");
        return target.get();
    }
}
