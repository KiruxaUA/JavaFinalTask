package com.gridu.qa.service;

import com.gridu.qa.model.Course;
import com.gridu.qa.model.Student;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The TrainingCenterService class represents a class with methods for calculating end date time,
 * getting status training completion, generating short and full reports.
 */
public class TrainingCenterService {
    public LocalDateTime calculateEndDateTime(Student student) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.of(student.getStartDate(), Constants.WORKING_START_TIME);
            int remainingHours = student.getCourses().stream().mapToInt(Course::getDurationHours).sum();
            while (remainingHours > 0) {
                if (currentDateTime.getDayOfWeek() != DayOfWeek.SATURDAY && currentDateTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    int hoursInCurrentDay = (int) Duration.between(currentDateTime.toLocalTime(), Constants.WORKING_END_TIME).toHours();
                    int hoursToSubtract = Math.min(hoursInCurrentDay, remainingHours);
                    currentDateTime = currentDateTime.plusHours(hoursToSubtract);
                    remainingHours -= hoursToSubtract;
                }
                if (remainingHours > 0) {
                    currentDateTime = currentDateTime.plusDays(1).withHour(Constants.WORKING_START_TIME.getHour());
                }
            }

            return currentDateTime;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return null;
        }
    }

    public String getStatus(Student student, LocalDateTime reportDateTime) {
        LocalDateTime endDateTime = calculateEndDateTime(student);
        Duration duration = Duration.between(reportDateTime, endDateTime);
        if (duration.isNegative()) {
            return String.format("Training completed. %d hours have passed since the end.", Math.abs(duration.toHours()));
        } else {
            long days = duration.toDays();
            long hours = duration.minusDays(days).toHours();
            return String.format("Training is not finished. %d d %d hours are left until the end.", days, hours);
        }
    }

    public String generateShortReport(Student student, LocalDateTime reportDateTime) throws NullPointerException {
        String status = getStatus(student, reportDateTime);
        return String.format("%s (%s) - %s", student.getName(), student.getCurriculum(), status);
    }

    public String generateFullReport(Student student, LocalDateTime reportDateTime) throws NullPointerException {
        String status = getStatus(student, reportDateTime);
        int programDuration = student.getCourses().stream().mapToInt(Course::getDurationHours).sum();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String startDateStr = student.getStartDate().format(dateFormatter);
        String endDateStr = calculateEndDateTime(student).toLocalDate().format(dateFormatter);
        return String.format("Student name: %s\nWorking time (from 10 to 18)\nProgram name: %s\n" +
                        "Program duration (hours): %d\nStart date: %s\nEnd date: %s\n%s",
                student.getName(), student.getCurriculum(), programDuration, startDateStr, endDateStr, status);
    }
}

final class Constants {
    public static final LocalTime WORKING_START_TIME = LocalTime.of(10, 0);
    public static final LocalTime WORKING_END_TIME = LocalTime.of(18, 0);
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class.");
    }
}