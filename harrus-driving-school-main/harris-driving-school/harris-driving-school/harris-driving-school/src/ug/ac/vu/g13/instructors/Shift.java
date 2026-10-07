package ug.ac.vu.g13.instructors;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 2
 */
public class Shift extends BaseRecord {
    private Instructor instructor;
    private int day;
    private int startHour;
    private int endHour;

    public Shift(String id, Instructor instructor, int day, int startHour, int endHour) {
        super(id);
        if (day < 1 || day > 7) {
            throw new InvalidDataException("Day must be 1 to 7.");
        }
        if (startHour < 0 || endHour > 23 || endHour <= startHour) {
            throw new InvalidDataException("Hours must be 0 to 23 and the end must be after the start.");
        }
        this.instructor = instructor;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public int getDay() {
        return day;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public String describe() {
        return getId() + " | " + instructor.getName() + " | day " + day + " | " + startHour + ":00 to " + endHour + ":00";
    }

    public String toFileLine() {
        return getId() + "|" + instructor.getId() + "|" + day + "|" + startHour + "|" + endHour;
    }
}
