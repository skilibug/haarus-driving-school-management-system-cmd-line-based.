package ug.ac.vu.g13.maintenance;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.vehicles.Vehicle;

/**
 * @author SANGANYA
 */
public class FuelLog extends BaseRecord {
    private Vehicle vehicle;
    private double litres;
    private double cost;

    public FuelLog(String id, Vehicle vehicle, double litres, double cost) {
        super(id);
        if (litres <= 0 || cost <= 0) {
            throw new InvalidDataException("Litres and cost must be more than 0.");
        }
        this.vehicle = vehicle;
        this.litres = litres;
        this.cost = cost;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getLitres() {
        return litres;
    }

    public double getCost() {
        return cost;
    }

    public String describe() {
        return getId() + " | " + vehicle.getPlate() + " | " + litres + " litres | cost " + cost;
    }

    public String toFileLine() {
        return getId() + "|" + vehicle.getId() + "|" + litres + "|" + cost;
    }
}
