package ug.ac.vu.g13.vehicles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.FileStore;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 3
 */
public class VehicleService {
    private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

    public VehicleService() {
        ArrayList<String> lines = FileStore.readLines("vehicles.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                if (p[0].equals("CAR")) {
                    vehicles.add(new Car(p[1], p[2], Integer.parseInt(p[3]), Integer.parseInt(p[4])));
                } else {
                    vehicles.add(new Motorcycle(p[1], p[2], Integer.parseInt(p[3]), Integer.parseInt(p[4])));
                }
            } catch (Exception e) {
                System.out.println("Skipped a bad line in vehicles.txt");
            }
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < vehicles.size(); i++) {
            lines.add(vehicles.get(i).toFileLine());
        }
        FileStore.writeLines("vehicles.txt", lines);
    }

    private void checkPlate(String plate) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getPlate().equalsIgnoreCase(plate)) {
                throw new DuplicatePlateException("A vehicle with this plate already exists.");
            }
        }
    }

    public void addCar(String plate, int km, int seats) {
        checkPlate(plate);
        vehicles.add(new Car(BaseRecord.nextId("V", vehicles), plate, km, seats));
        save();
    }

    public void addMotorcycle(String plate, int km, int engineCc) {
        checkPlate(plate);
        vehicles.add(new Motorcycle(BaseRecord.nextId("V", vehicles), plate, km, engineCc));
        save();
    }

    public Vehicle findVehicle(String id) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId().equalsIgnoreCase(id)) {
                return vehicles.get(i);
            }
        }
        throw new InvalidDataException("No vehicle with ID " + id);
    }

    public void removeVehicle(String id) {
        vehicles.remove(findVehicle(id));
        save();
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public ArrayList<Vehicle> sortedByKm() {
        ArrayList<Vehicle> copy = new ArrayList<Vehicle>(vehicles);
        Collections.sort(copy, new Comparator<Vehicle>() {
            public int compare(Vehicle a, Vehicle b) {
                return b.getKm() - a.getKm();
            }
        });
        return copy;
    }
}
