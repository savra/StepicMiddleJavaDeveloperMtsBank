package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto.CourseRequestToUpdate;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findAll();

    Optional<Course> findById(long id);

    void updateCourse(long id, CourseRequestToUpdate requestToUpdate);

    List<Course> findByTitleWithPrefix(String titlePrefix);

    void createCourse(CourseRequestToUpdate request);

    void deleteCourse(long id);

    void updateAll();
}
