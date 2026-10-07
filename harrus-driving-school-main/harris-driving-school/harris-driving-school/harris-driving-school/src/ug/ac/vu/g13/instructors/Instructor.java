package ug.ac.vu.g13.instructors;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 2
 */
public class Instructor extends BaseRecord {
    private String name;
    private int years;

    public Instructor(String id, String name, int years) {
        super(id);
        setName(name);
        setYears(years);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 2) {
            throw new InvalidDataException("Name is too short.");
        }
        this.name = name;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        if (years < 0 || years > 60) {
            throw new InvalidDataException("Years of experience must be 0 to 60.");
        }
        this.years = years;
    }

    public String describe() {
        return getId() + " | " + name + " | " + years + " years experience";
    }

    public String toFileLine() {
        return getId() + "|" + name + "|" + years;
    }
}
