package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.repository;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.Course;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {

    private static final List<Course> courses = List.of(new Course(1L, "Петров А.В.", "Основы кройки и шитья"),
            new Course(2L, "Мошкина А.В", "Введение в архитектурный дизайн"));

    public List<Course> findAll() {
        return courses;
    }

    public Optional<Course> findById(Long id) {
        return Optional.empty();
    }
}
