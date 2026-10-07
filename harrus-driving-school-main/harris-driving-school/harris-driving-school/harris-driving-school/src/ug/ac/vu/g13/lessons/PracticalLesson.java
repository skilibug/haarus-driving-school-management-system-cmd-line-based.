package ug.ac.vu.g13.lessons;

import ug.ac.vu.g13.instructors.Instructor;
import ug.ac.vu.g13.students.Student;
import ug.ac.vu.g13.vehicles.Vehicle;

/**
 * @author Member 5
 */
public class PracticalLesson extends Lesson {
    private Vehicle vehicle;

    public PracticalLesson(String id, Student student, Instructor instructor, String date, String time,
                           Vehicle vehicle) {
        super(id, student, instructor, date, time);
        this.vehicle = vehicle;
    }

    public String type() {
        return "PRACTICAL";
    }

    public String extraInfo() {
        return "vehicle " + vehicle.getPlate();
    }

    public String extraValue() {
        return vehicle.getId();
    }
}
