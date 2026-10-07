package ug.ac.vu.g13.courses;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 4
 */
public class Course extends BaseRecord {
    private String name;
    private double fee;
    private int capacity;

    public Course(String id, String name, double fee, int capacity) {
        super(id);
        setName(name);
        setFee(fee);
        setCapacity(capacity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 3) {
            throw new InvalidDataException("Course name is too short.");
        }
        this.name = name;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        if (fee <= 0) {
            throw new InvalidDataException("Fee must be more than 0.");
        }
        this.fee = fee;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 1 || capacity > 200) {
            throw new InvalidDataException("Capacity must be 1 to 200.");
        }
        this.capacity = capacity;
    }

    public String describe() {
        return getId() + " | " + name + " | fee " + fee + " UGX | seats " + capacity;
    }

    public String toFileLine() {
        return getId() + "|" + name + "|" + fee + "|" + capacity;
    }
}
