package ug.ac.vu.g13.vehicles;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 3
 */
public abstract class Vehicle extends BaseRecord {
    private String plate;
    private int km;

    public Vehicle(String id, String plate, int km) {
        super(id);
        setPlate(plate);
        setKm(km);
    }

    public abstract String extraInfo();

    public abstract String extraValue();

    public abstract String type();

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        if (plate.length() < 3) {
            throw new InvalidDataException("Plate is too short.");
        }
        this.plate = plate.toUpperCase();
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        if (km < 0) {
            throw new InvalidDataException("Kilometres cannot be negative.");
        }
        this.km = km;
    }

    public String describe() {
        return getId() + " | " + type() + " | " + plate + " | " + km + " km | " + extraInfo();
    }

    public String toFileLine() {
        return type() + "|" + getId() + "|" + plate + "|" + km + "|" + extraValue();
    }
}
