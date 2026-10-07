package ug.ac.vu.g13.lessons;

import ug.ac.vu.g13.core.InputHelper;

/**
 * @author Member 5
 */
public class LessonsMenu {
    private LessonService service;

    public LessonsMenu(LessonService service) {
        this.service = service;
    }

    public void run() {
        String[] options = {"Add theory lesson", "Add practical lesson", "List lessons", "Change lesson date",
                "Remove lesson", "Report: lessons by date", "Overview"};
        while (true) {
            int choice = InputHelper.menu("LESSONS", options);
            if (choice == 0) {
                return;
            }
            try {
                if (choice == 1) {
                    String student = InputHelper.readText("Student ID: ");
                    String instructor = InputHelper.readText("Instructor ID: ");
                    String date = InputHelper.readText("Date (2026-11-30): ");
                    String time = InputHelper.readText("Time (09:30): ");
                    String topic = InputHelper.readText("Topic: ");
                    service.addTheory(student, instructor, date, time, topic);
                    System.out.println("Lesson saved.");
                } else if (choice == 2) {
                    String student = InputHelper.readText("Student ID: ");
                    String instructor = InputHelper.readText("Instructor ID: ");
                    String vehicle = InputHelper.readText("Vehicle ID: ");
                    String date = InputHelper.readText("Date (2026-11-30): ");
                    String time = InputHelper.readText("Time (09:30): ");
                    service.addPractical(student, instructor, vehicle, date, time);
                    System.out.println("Lesson saved.");
                } else if (choice == 3) {
                    InputHelper.printList("Lessons", service.getLessons());
                } else if (choice == 4) {
                    Lesson l = service.findLesson(InputHelper.readText("Lesson ID: "));
                    l.setDate(InputHelper.readText("New date (2026-11-30): "));
                    service.save();
                    System.out.println("Changed.");
                } else if (choice == 5) {
                    service.removeLesson(InputHelper.readText("Lesson ID: "));
                    System.out.println("Lesson removed.");
                } else if (choice == 6) {
                    InputHelper.printList("Lessons by date", service.sortedByDate());
                } else if (choice == 7) {
                    for (int i = 0; i < service.getLessons().size(); i++) {
                        Lesson l = service.getLessons().get(i);
                        System.out.println(l.type() + " on " + l.getDate() + " -> " + l.extraInfo());
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
