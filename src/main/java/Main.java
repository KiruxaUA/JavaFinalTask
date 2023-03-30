import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Course> javaDeveloperCOurses = Arrays.asList(
                new Course("Java", 16),
                new Course("JDBC", 24),
                new Course("Spring", 16)
        );

        List<Course> aqeCourses = Arrays.asList(
                new Course("test design", 10),
                new Course("Page Object", 16),
                new Course("Selenium", 16)
        );

        Student ivanov = new Student("Ivanov Ivan", "Java Developer",
                LocalDate.of(2020, 6, 1), javaDeveloperCOurses);
        Student sidorov = new Student("Sidorov Ivan", "AQE",
                LocalDate.of(2020, 6, 1), aqeCourses);

        List<Student> students = Arrays.asList(ivanov, sidorov);

        LocalDateTime reportDateTime = LocalDateTime.of(2020, 6, 8, 15, 0);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a report type parameter: ");
        String reportType = scanner.next();

        for (Student student : students) {
            String report;
            if (reportType.equals("0") || reportType.equals("")) {
                report = TrainingCenter.generateShortReport(student, reportDateTime);
            } else {
                report = TrainingCenter.generateFullReport(student, reportDateTime);
            }
            System.out.println(report);
        }
    }
}
