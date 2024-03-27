package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto.CourseRequestToUpdate;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.Course;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNullElse;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> findById(long id) {
        return courseRepository.findById(id);
    }

    @Override
    public void updateCourse(long id, CourseRequestToUpdate requestToUpdate) {
        Course course = courseRepository.findById(id).orElseThrow();
        course.setTitle(requestToUpdate.getTitle());
        course.setAuthor(requestToUpdate.getAuthor());
    }

    @Override
    public List<Course> findByTitleWithPrefix(String titlePrefix) {
        return courseRepository.findByTitleWithPrefix(requireNonNullElse(titlePrefix, ""));
    }

    @Override
    public void createCourse(CourseRequestToUpdate request) {
        courseRepository.findAll().stream()
                .max(Comparator.comparing(Course::getId))
                .map(Course::getId)
                .ifPresentOrElse(
                        id -> courseRepository.save(new Course(id + 1, request.getAuthor(), request.getTitle())),
                        () -> courseRepository.save(new Course(1L, request.getAuthor(), request.getTitle())));
    }

    @Override
    public void deleteCourse(long id) {
        Course course = courseRepository.findById(id).orElseThrow();
        courseRepository.delete(course);
    }
}
