package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@AllArgsConstructor
@Table(name = "courses")
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Course {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotBlank(message = "Course author have to be filled")
    private String author;
    @NotBlank(message = "Course title have to be filled")
    private String title;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    public Course(Long id, String author, String title) {
        this.id = id;
        this.author = author;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return getId().equals(course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
