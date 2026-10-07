package ug.ac.vu.g13.app;

import ug.ac.vu.g13.core.InputHelper;
import ug.ac.vu.g13.courses.CourseService;
import ug.ac.vu.g13.courses.CoursesMenu;
import ug.ac.vu.g13.fees.FeeService;
import ug.ac.vu.g13.fees.FeesMenu;
import ug.ac.vu.g13.instructors.InstructorService;
import ug.ac.vu.g13.instructors.InstructorsMenu;
import ug.ac.vu.g13.lessons.LessonService;
import ug.ac.vu.g13.lessons.LessonsMenu;
import ug.ac.vu.g13.maintenance.MaintenanceMenu;
import ug.ac.vu.g13.maintenance.MaintenanceService;
import ug.ac.vu.g13.students.StudentService;
import ug.ac.vu.g13.students.StudentsMenu;
import ug.ac.vu.g13.tests.TestService;
import ug.ac.vu.g13.tests.TestsMenu;
import ug.ac.vu.g13.vehicles.VehicleService;
import ug.ac.vu.g13.vehicles.VehiclesMenu;

/**
@author Kefas
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("HARRIS DRIVING SCHOOL SYSTEM (Group G13)");

        StudentService students = new StudentService();
        InstructorService instructors = new InstructorService();
        VehicleService vehicles = new VehicleService();
        CourseService courses = new CourseService(students);
        LessonService lessons = new LessonService(students, instructors, vehicles);
        FeeService fees = new FeeService(courses);
        TestService tests = new TestService(students);
        MaintenanceService maintenance = new MaintenanceService(vehicles);

        StudentsMenu studentsMenu = new StudentsMenu(students);
        InstructorsMenu instructorsMenu = new InstructorsMenu(instructors);
        VehiclesMenu vehiclesMenu = new VehiclesMenu(vehicles);
        CoursesMenu coursesMenu = new CoursesMenu(courses);
        LessonsMenu lessonsMenu = new LessonsMenu(lessons);
        FeesMenu feesMenu = new FeesMenu(fees);
        TestsMenu testsMenu = new TestsMenu(tests);
        MaintenanceMenu maintenanceMenu = new MaintenanceMenu(maintenance);

        String[] options = {"Students", "Instructors", "Vehicles", "Courses", "Lessons", "Fees",
                "Driving Tests", "Maintenance and Fuel"};
        while (true) {
            int choice = InputHelper.menu("MAIN MENU", options);
            if (choice == 0) {
                System.out.println("Goodbye.");
                return;
            } else if (choice == 1) {
                studentsMenu.run();
            } else if (choice == 2) {
                instructorsMenu.run();
            } else if (choice == 3) {
                vehiclesMenu.run();
            } else if (choice == 4) {
                coursesMenu.run();
            } else if (choice == 5) {
                lessonsMenu.run();
            } else if (choice == 6) {
                feesMenu.run();
            } else if (choice == 7) {
                testsMenu.run();
            } else if (choice == 8) {
                maintenanceMenu.run();
            }
        }
    }
}
