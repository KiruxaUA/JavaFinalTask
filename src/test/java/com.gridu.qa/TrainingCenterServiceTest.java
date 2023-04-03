package com.gridu.qa;

import com.gridu.qa.model.Course;
import com.gridu.qa.model.Student;
import com.gridu.qa.service.TrainingCenterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrainingCenterServiceTest {

    static Stream<Object[]> testDataProvider() {
        Student student1 = new Student("Ivanov Ivan", "Java Developer", LocalDate.of(2020, 6, 1), List.of(
                new Course("Java", 16),
                new Course("JDBC", 24),
                new Course("Spring", 16)
        ));

        Student student2 = new Student("Sidorov Ivan", "AQE", LocalDate.of(2020, 6, 1), List.of(
                new Course("Test design", 10),
                new Course("Page Object", 16),
                new Course("Selenium", 16)
        ));

        return Stream.of(
                new Object[]{student1, LocalDateTime.of(2020, 6, 8, 15, 0), "2020-06-09T18:00", "Training is not finished. 1 d 3 hours are left until the end.", "Ivanov Ivan (Java Developer) - Training is not finished. 1 d 3 hours are left until the end."},
                new Object[]{student2, LocalDateTime.of(2020, 6, 8, 15, 0), "2020-06-08T12:00", "Training completed. 3 hours have passed since the end.", "Sidorov Ivan (AQE) - Training completed. 3 hours have passed since the end."}
        );
    }

    TrainingCenterService trainingCenterService = new TrainingCenterService();

    @ParameterizedTest
    @MethodSource("testDataProvider")
    void testCalculateEndDateTime(Student student, LocalDateTime reportDateTime, String expectedEndDateTimeStr, String expectedStatus, String expectedShortReport) {
        LocalDateTime expectedEndDateTime = LocalDateTime.parse(expectedEndDateTimeStr);
        LocalDateTime actualEndDateTime = trainingCenterService.calculateEndDateTime(student);

        assertEquals(expectedEndDateTime, actualEndDateTime);
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    void testGetStatus(Student student, LocalDateTime reportDateTime, String expectedEndDateTimeStr, String expectedStatus, String expectedShortReport) {
        String actualStatus = trainingCenterService.getStatus(student, reportDateTime);

        assertEquals(expectedStatus, actualStatus);
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    void testGenerateShortReport(Student student, LocalDateTime reportDateTime, String expectedEndDateTimeStr, String expectedStatus, String expectedShortReport) {
        String actualShortReport = trainingCenterService.generateShortReport(student, reportDateTime);

        assertEquals(expectedShortReport, actualShortReport);
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    void testGenerateFullReport(Student student, LocalDateTime reportDateTime, String expectedEndDateTimeStr, String expectedStatus, String expectedShortReport) {
        String expectedFullReport = String.format(
                "Student name: %s\nWorking time (from 10 to 18)\nProgram name: %s\nProgram duration (hours): %d\nStart date: %s\nEnd date: %s\n%s",
                student.getName(),
                student.getCurriculum(),
                student.getCourses().stream().mapToInt(Course::getDurationHours).sum(),
                student.getStartDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy")),
                LocalDateTime.parse(expectedEndDateTimeStr).toLocalDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy")),
                expectedStatus
        );

        String actualFullReport = trainingCenterService.generateFullReport(student, reportDateTime);
        assertEquals(expectedFullReport, actualFullReport);
    }

    @Test
    void generateShortReport_throwsNullPointerException() {
        Student invalidStudent = new Student("Vasya Pupkin", "Curriculum", null, new ArrayList<>());
        LocalDateTime invalidReportDateTime = LocalDateTime.of(1900, 1, 1, 0, 0);
        assertThrows(
                NullPointerException.class,
                () -> trainingCenterService.generateShortReport(invalidStudent, invalidReportDateTime));
    }

    @Test
    void generateFullReport_throwsNullPointerException() {
        Student invalidStudent = new Student("Vasya Pupkin", "Curriculum", LocalDate.now(), null);
        LocalDateTime invalidReportDateTime = null;
        assertThrows(
                NullPointerException.class,
                () -> trainingCenterService.generateFullReport(invalidStudent, invalidReportDateTime));
    }
}