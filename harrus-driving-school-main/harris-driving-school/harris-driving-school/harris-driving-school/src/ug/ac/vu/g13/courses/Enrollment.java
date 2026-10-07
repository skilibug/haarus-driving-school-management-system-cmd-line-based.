package ug.ac.vu.g13.courses;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.students.Student;

/**
 * @author Member 4
 */
public class Enrollment extends BaseRecord {
    private Student student;
    private Course course;

    public Enrollment(String id, Student student, Course course) {
        super(id);
        this.student = student;
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String describe() {
        return getId() + " | " + student.getName() + " joined " + course.getName();
    }

    public String toFileLine() {
        return getId() + "|" + student.getId() + "|" + course.getId();
    }
}
