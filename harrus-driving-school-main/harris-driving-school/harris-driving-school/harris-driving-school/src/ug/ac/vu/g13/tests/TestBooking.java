package ug.ac.vu.g13.tests;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.students.Student;

/**
 * @author Member 7
 */
public class TestBooking extends BaseRecord {
    private Student student;
    private String type;
    private String date;

    public TestBooking(String id, Student student, String type, String date) {
        super(id);
        if (!type.equals("THEORY") && !type.equals("PRACTICAL")) {
            throw new InvalidDataException("Type must be THEORY or PRACTICAL.");
        }
        this.student = student;
        this.type = type;
        setDate(date);
    }

    public void setDate(String date) {
        if (date.length() != 10) {
            throw new InvalidDataException("Date must look like 2026-11-30.");
        }
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public String getType() {
        return type;
    }

    public String describe() {
        return getId() + " | " + student.getName() + " | " + type + " test | " + date;
    }

    public String toFileLine() {
        return getId() + "|" + student.getId() + "|" + type + "|" + date;
    }
}
