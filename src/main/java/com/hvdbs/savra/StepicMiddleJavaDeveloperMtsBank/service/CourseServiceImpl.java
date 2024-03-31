package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto.CourseRequestToUpdate;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.Course;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

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

        courseRepository.save(course);
    }

    @Override
    public List<Course> findByTitleWithPrefix(String titlePrefix) {
        return courseRepository.findByTitleLike(titlePrefix + "%");
    }

    @Override
    public void createCourse(CourseRequestToUpdate request) {
        Course course = new Course();
        course.setAuthor(request.getAuthor());
        course.setTitle(request.getTitle());

        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(long id) {
        Course course = courseRepository.findById(id).orElseThrow();
        courseRepository.delete(course);
    }
}
