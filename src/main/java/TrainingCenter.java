import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The TrainingCenter class represents a training center with static variables working start time and working end time.
 * It provides methods calculating end date time, generating a short report, generating a full report.
 */
public class TrainingCenter {
    private static final LocalTime WORKING_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime WORKING_END_TIME = LocalTime.of(18, 0);

    public static LocalDateTime calculateEndDateTime(Student student) {
        LocalDateTime currentDateTime = LocalDateTime.of(student.startDate, WORKING_START_TIME);
        int remainingHours = student.courses.stream().mapToInt(course -> course.durationHours).sum();

        while (remainingHours > 0) {
            if (currentDateTime.getDayOfWeek() != DayOfWeek.SATURDAY && currentDateTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
                int hoursInCurrentDay = (int) Duration.between(currentDateTime.toLocalTime(), WORKING_END_TIME).toHours();
                int hoursToSubtract = Math.min(hoursInCurrentDay, remainingHours);
                currentDateTime = currentDateTime.plusHours(hoursToSubtract);
                remainingHours -= hoursToSubtract;
            }

            if (remainingHours > 0) {
                currentDateTime = currentDateTime.plusDays(1).withHour(WORKING_START_TIME.getHour());
            }
        }

        return currentDateTime;
    }

    public static String getStatus(Student student, LocalDateTime reportDateTime) {
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

    public static String generateShortReport(Student student, LocalDateTime reportDateTime) {
        String status = getStatus(student, reportDateTime);
        return String.format("%s (%s) - %s", student.name, student.curriculum, status);
    }

    public static String generateFullReport(Student student, LocalDateTime reportDateTime) {
        String status = getStatus(student, reportDateTime);
        int programDuration = student.courses.stream().mapToInt(course -> course.durationHours).sum();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String startDateStr = student.startDate.format(dateFormatter);
        String endDateStr = calculateEndDateTime(student).toLocalDate().format(dateFormatter);

        return String.format("Student name: %s\nWorking time (from 10 to 18)\nProgram name: %s\n" +
                        "Program duration (hours): %d\nStart date: %s\nEnd date: %s\n%s",
                student.name, student.curriculum, programDuration, startDateStr, endDateStr, status);
    }
}
