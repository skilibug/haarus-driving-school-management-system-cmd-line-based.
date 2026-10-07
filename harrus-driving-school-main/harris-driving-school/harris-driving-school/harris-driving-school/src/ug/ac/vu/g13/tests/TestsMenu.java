package ug.ac.vu.g13.tests;

import java.util.ArrayList;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InputHelper;

/**
 * @author Member 7
 */
public class TestsMenu {
    private TestService service;

    public TestsMenu(TestService service) {
        this.service = service;
    }

    public void run() {
        String[] options = {"Book a test", "List bookings", "Change booking date", "Remove booking",
                "Record result", "List results", "Report: highest score first", "Overview"};
        while (true) {
            int choice = InputHelper.menu("DRIVING TESTS", options);
            if (choice == 0) {
                return;
            }
            try {
                if (choice == 1) {
                    String student = InputHelper.readText("Student ID: ");
                    String type = InputHelper.readText("Type (THEORY or PRACTICAL): ").toUpperCase();
                    String date = InputHelper.readText("Date (2026-11-30): ");
                    service.addBooking(student, type, date);
                    System.out.println("Booking saved.");
                } else if (choice == 2) {
                    InputHelper.printList("Bookings", service.getBookings());
                } else if (choice == 3) {
                    TestBooking b = service.findBooking(InputHelper.readText("Booking ID: "));
                    b.setDate(InputHelper.readText("New date (2026-11-30): "));
                    service.save();
                    System.out.println("Date changed.");
                } else if (choice == 4) {
                    service.removeBooking(InputHelper.readText("Booking ID: "));
                    System.out.println("Booking removed.");
                } else if (choice == 5) {
                    String id = InputHelper.readText("Booking ID: ");
                    int score = InputHelper.readInt("Score (0-100): ", 0, 100);
                    service.addResult(id, score);
                    System.out.println("Result saved.");
                } else if (choice == 6) {
                    InputHelper.printList("Results", service.getResults());
                } else if (choice == 7) {
                    InputHelper.printList("Highest score first", service.sortedByScore());
                } else if (choice == 8) {
                    ArrayList<BaseRecord> all = new ArrayList<BaseRecord>();
                    all.addAll(service.getBookings());
                    all.addAll(service.getResults());
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
