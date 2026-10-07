package ug.ac.vu.g13.lessons;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.instructors.Instructor;
import ug.ac.vu.g13.students.Student;

/**
 * @author Member 5
 */
public abstract class Lesson extends BaseRecord {
    private Student student;
    private Instructor instructor;
    private String date;
    private String time;

    public Lesson(String id, Student student, Instructor instructor, String date, String time) {
        super(id);
        this.student = student;
        this.instructor = instructor;
        setDate(date);
        setTime(time);
    }

    // Each child class gives its own extra information.
    public abstract String extraInfo();

    public abstract String type();

    public Student getStudent() {
        return student;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (date.length() != 10) {
            throw new InvalidDataException("Date must look like 2026-11-30.");
        }
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        if (time.length() != 5) {
            throw new InvalidDataException("Time must look like 09:30.");
        }
        this.time = time;
    }

    public String describe() {
        return getId() + " | " + type() + " | " + date + " " + time + " | " + student.getName() + " with "
                + instructor.getName() + " | " + extraInfo();
    }

    public String toFileLine() {
        return type() + "|" + getId() + "|" + student.getId() + "|" + instructor.getId() + "|" + date + "|" + time
                + "|" + extraValue();
    }

    public abstract String extraValue();
}
