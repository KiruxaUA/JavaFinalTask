package com.gridu.qa.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * The Student class represents a student with a name, curriculum, start date anc courses.
 * It provides constructor for setting up name, curriculum, startDate, courses fields,
 * Getter and setters for these fields and equals and hashcode realization.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private String curriculum;
    private LocalDate startDate;
    private List<Course> courses;
}