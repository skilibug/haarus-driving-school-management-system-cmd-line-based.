package ug.ac.vu.g13.maintenance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.FileStore;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.vehicles.VehicleService;

/**
@author SANGANYA
 */
public class MaintenanceService {
    private ArrayList<MaintenanceJob> jobs = new ArrayList<MaintenanceJob>();
    private ArrayList<FuelLog> fuelLogs = new ArrayList<FuelLog>();
    private VehicleService vehicleService;

    public MaintenanceService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
        ArrayList<String> lines = FileStore.readLines("maintenance.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                jobs.add(new MaintenanceJob(p[0], vehicleService.findVehicle(p[1]), p[2], Double.parseDouble(p[3]),
                        Boolean.parseBoolean(p[4])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in maintenance.txt");
            }
        }
        lines = FileStore.readLines("fuel.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                fuelLogs.add(new FuelLog(p[0], vehicleService.findVehicle(p[1]), Double.parseDouble(p[2]),
                        Double.parseDouble(p[3])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in fuel.txt");
            }
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < jobs.size(); i++) {
            lines.add(jobs.get(i).toFileLine());
        }
        FileStore.writeLines("maintenance.txt", lines);
        lines = new ArrayList<String>();
        for (int i = 0; i < fuelLogs.size(); i++) {
            lines.add(fuelLogs.get(i).toFileLine());
        }
        FileStore.writeLines("fuel.txt", lines);
    }

    public void addJob(String vehicleId, String description) {
        jobs.add(new MaintenanceJob(BaseRecord.nextId("M", jobs), vehicleService.findVehicle(vehicleId),
                description, 0, false));
        save();
    }

    public MaintenanceJob findJob(String id) {
        for (int i = 0; i < jobs.size(); i++) {
            if (jobs.get(i).getId().equalsIgnoreCase(id)) {
                return jobs.get(i);
            }
        }
        throw new InvalidDataException("No job with ID " + id);
    }

    public void closeJob(String id, double cost) {
        MaintenanceJob job = findJob(id);
        if (job.isClosed()) {
            throw new JobAlreadyClosedException("Job " + id + " is already closed.");
        }
        job.setCost(cost);
        job.setClosed(true);
        save();
    }

    public void removeJob(String id) {
        jobs.remove(findJob(id));
        save();
    }

    public void addFuel(String vehicleId, double litres, double cost) {
        fuelLogs.add(new FuelLog(BaseRecord.nextId("F", fuelLogs), vehicleService.findVehicle(vehicleId), litres, cost));
        save();
    }

    public ArrayList<MaintenanceJob> getJobs() {
        return jobs;
    }

    public ArrayList<FuelLog> getFuelLogs() {
        return fuelLogs;
    }

    public ArrayList<MaintenanceJob> sortedByCost() {
        ArrayList<MaintenanceJob> copy = new ArrayList<MaintenanceJob>(jobs);
        Collections.sort(copy, new Comparator<MaintenanceJob>() {
            public int compare(MaintenanceJob a, MaintenanceJob b) {
                return Double.compare(b.getCost(), a.getCost());
            }
        });
        return copy;
    }
}
