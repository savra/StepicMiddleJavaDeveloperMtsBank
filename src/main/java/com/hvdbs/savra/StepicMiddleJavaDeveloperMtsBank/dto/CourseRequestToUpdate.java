package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto;

import javax.validation.constraints.NotBlank;

public class CourseRequestToUpdate {
    @NotBlank(message = "Course author has to be filled")
    private String author;
    @TitleCase
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
