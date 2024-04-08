package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.controller;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model.Course;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service.CourseService;
import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@WithMockUser(roles = "STUDENT")
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;

    @Test
    void courseTableShouldReturnCoursesList() throws Exception {
        List<Course> courses = List.of(
                new Course(1L, "test author1", "test title1"),
                new Course(2L, "test author2", "test title2"),
                new Course(3L, "test author3", "test title3"));

        when(courseService.findAll()).thenReturn(courses);


        mockMvc.perform(MockMvcRequestBuilders.get("/course"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.size()", equalTo(3)),
                        jsonPath("$[0].id", is(courses.get(0).getId().intValue())),
                        jsonPath("$[0].author", equalTo(courses.get(0).getAuthor())),
                        jsonPath("$[0].title", equalTo(courses.get(0).getTitle())),
                        jsonPath("$[1].id", is(courses.get(1).getId().intValue())),
                        jsonPath("$[1].author", equalTo(courses.get(1).getAuthor())),
                        jsonPath("$[1].title", equalTo(courses.get(1).getTitle())),
                        jsonPath("$[2].id", is(courses.get(2).getId().intValue())),
                        jsonPath("$[2].author", equalTo(courses.get(2).getAuthor())),
                        jsonPath("$[2].title", equalTo(courses.get(2).getTitle()))
                );
    }
}