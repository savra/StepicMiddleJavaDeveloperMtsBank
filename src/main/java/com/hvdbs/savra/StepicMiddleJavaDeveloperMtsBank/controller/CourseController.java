package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.controller;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto.CourseRequestToUpdate;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.Course;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
   // private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService/*,
                            @Qualifier("userServiceImpl") UserService userService*/) {
        this.courseService = courseService;
       /* this.userService = userService;*/
    }

    @GetMapping
    public List<Course> courseTable() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseService.findById(id).orElseThrow();
    }

    @PostMapping
    public void createCourse(@Valid @RequestBody CourseRequestToUpdate request) {
        courseService.createCourse(request);
    }

    @PutMapping("/{id}")
    public void updateCourse(@PathVariable long id,
                             @Valid @RequestBody CourseRequestToUpdate request) {
        courseService.updateCourse(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/filter")
    public List<Course> getCoursesByTitlePrefix(@RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        return courseService.findByTitleWithPrefix(requireNonNullElse(titlePrefix, ""));
    }

   /* @GetMapping("/test")
    public void test() {
        userService.getCurrentUser();
        userService.test2(); // При вызове test2 не будет вызван getCurrentUser, т.к. это внутренний вызов
    }*/
}
