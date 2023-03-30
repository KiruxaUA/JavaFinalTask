import java.time.LocalDate;
import java.util.List;

/**
 * The Student class represents a student with a name, curriculum, start date anc courses.
 * It provides constructor for setting up name, curriculum, startDate, courses fields..
 */
public class Student {
    String name;
    String curriculum;
    LocalDate startDate;
    List<Course> courses;

    public Student(String name, String curriculum, LocalDate startDate, List<Course> courses) {
        this.name = name;
        this.curriculum = curriculum;
        this.startDate = startDate;
        this.courses = courses;
    }
}