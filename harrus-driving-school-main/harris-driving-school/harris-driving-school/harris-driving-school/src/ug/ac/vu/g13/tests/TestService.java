package ug.ac.vu.g13.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.FileStore;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.students.StudentService;

/**
 * @author Member 7
 */
public class TestService {
    private ArrayList<TestBooking> bookings = new ArrayList<TestBooking>();
    private ArrayList<TestResult> results = new ArrayList<TestResult>();
    private StudentService studentService;

    public TestService(StudentService studentService) {
        this.studentService = studentService;
        ArrayList<String> lines = FileStore.readLines("test_bookings.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                bookings.add(new TestBooking(p[0], studentService.findStudent(p[1]), p[2], p[3]));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in test_bookings.txt");
            }
        }
        lines = FileStore.readLines("test_results.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                results.add(new TestResult(p[0], findBooking(p[1]), Integer.parseInt(p[2])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in test_results.txt");
            }
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < bookings.size(); i++) {
            lines.add(bookings.get(i).toFileLine());
        }
        FileStore.writeLines("test_bookings.txt", lines);
        lines = new ArrayList<String>();
        for (int i = 0; i < results.size(); i++) {
            lines.add(results.get(i).toFileLine());
        }
        FileStore.writeLines("test_results.txt", lines);
    }

    public void addBooking(String studentId, String type, String date) {
        bookings.add(new TestBooking(BaseRecord.nextId("T", bookings), studentService.findStudent(studentId),
                type, date));
        save();
    }

    public TestBooking findBooking(String id) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId().equalsIgnoreCase(id)) {
                return bookings.get(i);
            }
        }
        throw new InvalidDataException("No test booking with ID " + id);
    }

    public void removeBooking(String id) {
        TestBooking b = findBooking(id);
        for (int i = results.size() - 1; i >= 0; i--) {
            if (results.get(i).getBooking() == b) {
                results.remove(i);
            }
        }
        bookings.remove(b);
        save();
    }

    public void addResult(String bookingId, int score) {
        TestBooking b = findBooking(bookingId);
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getBooking() == b) {
                throw new TestAlreadyTakenException("This booking already has a result.");
            }
        }
        results.add(new TestResult(BaseRecord.nextId("R", results), b, score));
        save();
    }

    public ArrayList<TestBooking> getBookings() {
        return bookings;
    }

    public ArrayList<TestResult> getResults() {
        return results;
    }

    public ArrayList<TestResult> sortedByScore() {
        ArrayList<TestResult> copy = new ArrayList<TestResult>(results);
        Collections.sort(copy, new Comparator<TestResult>() {
            public int compare(TestResult a, TestResult b) {
                return b.getScore() - a.getScore();
            }
        });
        return copy;
    }
}
