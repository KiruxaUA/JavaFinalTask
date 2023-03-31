package com.gridu.qa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The Course class represents a course with a name and duration in hours.
 * It provides constructor to set values for name and durationHours fields,
 * Getter and setters for these fields and equals and hashcode realization.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class Course {
    private String name;
    private int durationHours;
}