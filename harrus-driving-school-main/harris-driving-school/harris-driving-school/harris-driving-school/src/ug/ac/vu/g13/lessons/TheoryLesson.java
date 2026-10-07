package ug.ac.vu.g13.lessons;

import ug.ac.vu.g13.instructors.Instructor;
import ug.ac.vu.g13.students.Student;

/**
 * @author Member 5
 */
public class TheoryLesson extends Lesson {
    private String topic;

    public TheoryLesson(String id, Student student, Instructor instructor, String date, String time, String topic) {
        super(id, student, instructor, date, time);
        this.topic = topic;
    }

    public String type() {
        return "THEORY";
    }

    public String extraInfo() {
        return "topic: " + topic;
    }

    public String extraValue() {
        return topic;
    }
}
