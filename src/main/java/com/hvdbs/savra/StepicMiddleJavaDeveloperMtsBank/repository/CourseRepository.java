package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.repository;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CourseRepository {

    private static final List<Course> courses = new ArrayList<>();

    static {
        courses.add(new Course(1L, "Петров А.В.", "Основы кройки и шитья"));
        courses.add(new Course(2L, "Мошкина А.В", "Введение в архитектурный дизайн"));
    }

    public List<Course> findAll() {
        return courses;
    }

    public Optional<Course> findById(Long id) {
        return courses.stream()
                .filter(course -> course.getId().equals(id))
                .findFirst();
    }

    public void save(Course course) {
        courses.add(course);
    }

    public List<Course> findByTitleWithPrefix(String prefix) {
        return courses.stream()
                .filter(course -> course.getTitle().startsWith(prefix))
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        courses.removeIf(course -> course.getId().equals(id));
    }
}
