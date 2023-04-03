package com.gridu.qa.model;

import lombok.*;

/**
 * The Course class represents a course with a name and duration in hours.
 * It provides constructor to set values for name and durationHours fields,
 * Getter and setters for these fields and equals and hashcode realization.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private String name;
    private int durationHours;
}