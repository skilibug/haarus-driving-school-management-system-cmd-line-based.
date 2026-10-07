package ug.ac.vu.g13.vehicles;

import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 3
 */
public class Motorcycle extends Vehicle {
    private int engineCc;

    public Motorcycle(String id, String plate, int km, int engineCc) {
        super(id, plate, km);
        if (engineCc < 50 || engineCc > 2000) {
            throw new InvalidDataException("Engine size must be 50 to 2000 cc.");
        }
        this.engineCc = engineCc;
    }

    public String type() {
        return "MOTORCYCLE";
    }

    public String extraInfo() {
        return engineCc + " cc";
    }

    public String extraValue() {
        return String.valueOf(engineCc);
    }
}
