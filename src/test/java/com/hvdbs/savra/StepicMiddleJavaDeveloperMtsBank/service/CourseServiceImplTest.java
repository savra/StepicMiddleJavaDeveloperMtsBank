package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.config.TestConfig;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.Course;

import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class CourseServiceImplTest extends TestConfig {
    @Autowired
    private CourseService courseService;

    @Test
    void findAllShouldReturnAllCourses() {
        List<Course> courses = courseService.findAll();

        Assertions.assertAll(
                () -> MatcherAssert.assertThat(courses.size(), is(2))
        );
    }
}