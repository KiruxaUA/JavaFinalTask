package com.gridu.qa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * The Student class represents a student with a name, curriculum, start date anc courses.
 * It provides constructor for setting up name, curriculum, startDate, courses fields,
 * Getter and setters for these fields and equals and hashcode realization.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class Student {
    private String name;
    private String curriculum;
    private LocalDate startDate;
    private List<Course> courses;
}