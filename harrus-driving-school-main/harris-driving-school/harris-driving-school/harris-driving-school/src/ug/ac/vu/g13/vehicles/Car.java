package ug.ac.vu.g13.vehicles;

import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 3
 */
public class Car extends Vehicle {
    private int seats;

    public Car(String id, String plate, int km, int seats) {
        super(id, plate, km);
        if (seats < 2 || seats > 9) {
            throw new InvalidDataException("Seats must be 2 to 9.");
        }
        this.seats = seats;
    }

    public String type() {
        return "CAR";
    }

    public String extraInfo() {
        return seats + " seats";
    }

    public String extraValue() {
        return String.valueOf(seats);
    }
}
