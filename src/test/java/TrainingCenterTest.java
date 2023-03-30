import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainingCenterTest {

    private final List<Course> javaDeveloperCourses = Arrays.asList(
            new Course("Java", 16),
            new Course("JDBC", 24),
            new Course("Spring", 16)
    );

    @Test
    public void testCalculateEndDateTime() {
        Student student = new Student("Ivanov Ivan", "Java Developer", LocalDate.of(2020, 6, 1), javaDeveloperCourses);
        LocalDateTime expectedEndDateTime = LocalDateTime.of(2020, 6, 9, 18, 0);
        assertEquals(expectedEndDateTime, TrainingCenter.calculateEndDateTime(student));
    }

    @Test
    public void testGenerateShortReport() {
        Student student = new Student("Ivanov Ivan", "Java Developer", LocalDate.of(2020, 6, 1), javaDeveloperCourses);
        LocalDateTime reportDateTime = LocalDateTime.of(2020, 6, 8, 15, 0);
        String expectedReport = "Ivanov Ivan (Java Developer) - Training is not finished. 1 d 3 hours are left until the end.";
        assertEquals(expectedReport, TrainingCenter.generateShortReport(student, reportDateTime));
    }

    @Test
    public void testGenerateFullReport() {
        Student student = new Student("Ivanov Ivan", "Java Developer", LocalDate.of(2020, 6, 1), javaDeveloperCourses);
        LocalDateTime reportDateTime = LocalDateTime.of(2020, 6, 9, 15, 0);
        String expectedReport = "Student name: Ivanov Ivan\nWorking time (from 10 to 18)\nProgram name: Java Developer\n" +
                "Program duration (hours): 56\nStart date: 1 June 2020\nEnd date: 9 June 2020\n" +
                "Training is not finished. 0 d 3 hours are left until the end.";
        assertEquals(expectedReport, TrainingCenter.generateFullReport(student, reportDateTime));
    }
}