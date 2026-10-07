package ug.ac.vu.g13.instructors;

import java.util.ArrayList;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InputHelper;

/**
 * @author Member 2
 */
public class InstructorsMenu {
    private InstructorService service;

    public InstructorsMenu(InstructorService service) {
        this.service = service;
    }

    public void run() {
        String[] options = {"Register instructor", "List instructors", "Change years of experience",
                "Remove instructor", "Add shift", "List shifts", "Report: most experienced first", "Overview"};
        while (true) {
            int choice = InputHelper.menu("INSTRUCTORS", options);
            if (choice == 0) {
                return;
            }
            try {
                if (choice == 1) {
                    String name = InputHelper.readText("Name: ");
                    int years = InputHelper.readInt("Years of experience: ", 0, 100);
                    service.addInstructor(name, years);
                    System.out.println("Instructor saved.");
                } else if (choice == 2) {
                    InputHelper.printList("Instructors", service.getInstructors());
                } else if (choice == 3) {
                    Instructor ins = service.findInstructor(InputHelper.readText("Instructor ID: "));
                    ins.setYears(InputHelper.readInt("New years: ", 0, 100));
                    service.save();
                    System.out.println("Changed.");
                } else if (choice == 4) {
                    service.removeInstructor(InputHelper.readText("Instructor ID: "));
                    System.out.println("Instructor removed.");
                } else if (choice == 5) {
                    String id = InputHelper.readText("Instructor ID: ");
                    int day = InputHelper.readInt("Day (1=Monday ... 7=Sunday): ", 1, 7);
                    int start = InputHelper.readInt("Start hour (0-23): ", 0, 23);
                    int end = InputHelper.readInt("End hour (0-23): ", 0, 23);
                    service.addShift(id, day, start, end);
                    System.out.println("Shift saved.");
                } else if (choice == 6) {
                    InputHelper.printList("Shifts", service.getShifts());
                } else if (choice == 7) {
                    InputHelper.printList("By experience", service.sortedByExperience());
                } else if (choice == 8) {
                    ArrayList<BaseRecord> all = new ArrayList<BaseRecord>();
                    all.addAll(service.getInstructors());
                    all.addAll(service.getShifts());
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
