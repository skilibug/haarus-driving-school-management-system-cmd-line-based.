package ug.ac.vu.g13.students;

import java.util.ArrayList;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InputHelper;

/**
 * @author Kefas
 */
public class StudentsMenu {
    private StudentService service;

    public StudentsMenu(StudentService service) {
        this.service = service;
    }

    public void run() {
        String[] options = {"Register student", "List students", "Change student phone", "Remove student",
                "Add permit application", "List permits", "Report: students sorted by name", "Overview"};
        while (true) {
            int choice = InputHelper.menu("STUDENTS", options);
            if (choice == 0) {
                return;
            }
            try {
                if (choice == 1) {
                    String name = InputHelper.readText("Name: ");
                    String phone = InputHelper.readText("Phone (10 digits): ");
                    int age = InputHelper.readInt("Age: ", 0, 150);
                    service.addStudent(name, phone, age);
                    System.out.println("Student saved.");
                } else if (choice == 2) {
                    InputHelper.printList("Students", service.getStudents());
                } else if (choice == 3) {
                    Student s = service.findStudent(InputHelper.readText("Student ID: "));
                    s.setPhone(InputHelper.readText("New phone: "));
                    service.save();
                    System.out.println("Phone changed.");
                } else if (choice == 4) {
                    service.removeStudent(InputHelper.readText("Student ID: "));
                    System.out.println("Student removed.");
                } else if (choice == 5) {
                    service.addPermit(InputHelper.readText("Student ID: "));
                    System.out.println("Permit added.");
                } else if (choice == 6) {
                    InputHelper.printList("Permits", service.getPermits());
                } else if (choice == 7) {
                    InputHelper.printList("Students by name", service.sortedByName());
                } else if (choice == 8) {
                    ArrayList<BaseRecord> all = new ArrayList<BaseRecord>();
                    all.addAll(service.getStudents());
                    all.addAll(service.getPermits());
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
