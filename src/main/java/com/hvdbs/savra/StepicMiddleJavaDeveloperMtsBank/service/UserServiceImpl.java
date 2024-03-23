package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Auditable
    @LogExecutionTime
    @Override
    public User getCurrentUser() {
        System.out.println("Вызван метод из сервиса UserService");
        return new User();
    }

    @Override
    public Optional<User> test2() {
        try {
            System.out.println("Вызван метод test2");
            return Optional.of(getCurrentUser());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
