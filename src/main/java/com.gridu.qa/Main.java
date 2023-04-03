package com.gridu.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gridu.qa.model.Student;
import com.gridu.qa.service.TrainingCenterService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String reportTypeEnv = System.getenv("REPORT_TYPE");
        String reportDateEnv = System.getenv("REPORT_DATE");
        String studentsJsonEnv = System.getenv("STUDENTS_JSON");
        if (reportDateEnv == null || studentsJsonEnv == null) {
            System.out.println("Please set the REPORT_DATE and STUDENTS_JSON environment variables.");
            return;
        }
        boolean isShortReport = reportTypeEnv != null && reportTypeEnv.equals("0");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate reportDate = LocalDate.parse(reportDateEnv, dateFormatter);
        LocalDateTime reportDateTime = reportDate.atTime(15, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        TrainingCenterService trainingCenterService = new TrainingCenterService();
        List<Student> students;
        try {
            students = objectMapper.readValue(studentsJsonEnv, objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
        } catch (Exception e) {
            System.out.println("Error parsing students JSON: " + e.getMessage());
            return;
        }
        for (Student student : students) {
            String report = null;
            try {
                if (isShortReport) {
                    report = trainingCenterService.generateShortReport(student, reportDateTime);
                } else {
                    report = trainingCenterService.generateFullReport(student, reportDateTime);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
            System.out.println(Objects.requireNonNullElse(report, "Report generation failed."));
        }
    }
}