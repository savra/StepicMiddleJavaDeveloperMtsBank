package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class LogExecutionTimeUserService implements UserService {
    private final UserService userService;

    public LogExecutionTimeUserService(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getCurrentUser() {
        return logExecutionTime(userService::getCurrentUser);
    }

    @Override
    public Optional<User> test2() {
        return logExecutionTime(userService::test2);
    }

    private <T> T logExecutionTime(Supplier<T> targetMethod) {
        long start = System.nanoTime();

        System.out.println("Вызвался метод для замера времени выполнения");
        T t = targetMethod.get();
        System.out.println("Время выполнения метода заняло: " + (System.nanoTime() - start));

        return t;
    }
}
