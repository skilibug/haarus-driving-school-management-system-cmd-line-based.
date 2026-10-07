package ug.ac.vu.g13.courses;

import java.util.ArrayList;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InputHelper;

/**
 * @author Member 4
 */
public class CoursesMenu {
    private CourseService service;

    public CoursesMenu(CourseService service) {
        this.service = service;
    }

    public void run() {
        String[] options = {"Add course", "List courses", "Change course fee", "Remove course",
                "Enrol student", "List enrolments", "Report: cheapest course first", "Overview"};
        while (true) {
            int choice = InputHelper.menu("COURSES", options);
            if (choice == 0) {
                return;
            }
            try {
                if (choice == 1) {
                    String name = InputHelper.readText("Course name: ");
                    double fee = InputHelper.readDouble("Fee (UGX): ");
                    int seats = InputHelper.readInt("Seats: ", 0, 1000);
                    service.addCourse(name, fee, seats);
                    System.out.println("Course saved.");
                } else if (choice == 2) {
                    InputHelper.printList("Courses", service.getCourses());
                } else if (choice == 3) {
                    Course c = service.findCourse(InputHelper.readText("Course ID: "));
                    c.setFee(InputHelper.readDouble("New fee: "));
                    service.save();
                    System.out.println("Changed.");
                } else if (choice == 4) {
                    service.removeCourse(InputHelper.readText("Course ID: "));
                    System.out.println("Course removed.");
                } else if (choice == 5) {
                    String studentId = InputHelper.readText("Student ID: ");
                    String courseId = InputHelper.readText("Course ID: ");
                    service.enrol(studentId, courseId);
                    System.out.println("Student enrolled.");
                } else if (choice == 6) {
                    InputHelper.printList("Enrolments", service.getEnrollments());
                } else if (choice == 7) {
                    InputHelper.printList("Cheapest first", service.sortedByFee());
                } else if (choice == 8) {
                    ArrayList<BaseRecord> all = new ArrayList<BaseRecord>();
                    all.addAll(service.getCourses());
                    all.addAll(service.getEnrollments());
                    for (int i = 0; i < all.size(); i++) {
                        System.out.println(all.get(i).describe());
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
