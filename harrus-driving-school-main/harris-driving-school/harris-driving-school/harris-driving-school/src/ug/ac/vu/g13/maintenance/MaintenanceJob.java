package ug.ac.vu.g13.maintenance;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.vehicles.Vehicle;

/**
 * @author SANGANYA
 */
public class MaintenanceJob extends BaseRecord {
    private Vehicle vehicle;
    private String description;
    private double cost;
    private boolean closed;

    public MaintenanceJob(String id, Vehicle vehicle, String description, double cost, boolean closed) {
        super(id);
        if (description.length() < 3) {
            throw new InvalidDataException("Description is too short.");
        }
        this.vehicle = vehicle;
        this.description = description;
        setCost(cost);
        this.closed = closed;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if (cost < 0) {
            throw new InvalidDataException("Cost cannot be negative.");
        }
        this.cost = cost;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String describe() {
        String state = "OPEN";
        if (closed) {
            state = "CLOSED";
        }
        return getId() + " | " + vehicle.getPlate() + " | " + description + " | cost " + cost + " | " + state;
    }

    public String toFileLine() {
        return getId() + "|" + vehicle.getId() + "|" + description + "|" + cost + "|" + closed;
    }
}
