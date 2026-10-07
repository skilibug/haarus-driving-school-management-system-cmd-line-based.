package ug.ac.vu.g13.maintenance;

import java.util.ArrayList;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InputHelper;

/**
 * @author SANGANYA
 */
public class MaintenanceMenu {
    private MaintenanceService service;

    public MaintenanceMenu(MaintenanceService service) {
        this.service = service;
    }

    public void run() {
        String[] options = {"Open maintenance job", "List jobs", "Close job", "Remove job", "Add fuel log",
                "List fuel logs", "Report: most expensive job first", "Overview"};
        while (true) {
            int choice = InputHelper.menu("MAINTENANCE AND FUEL", options);
            if (choice == 0) {
                return;
            }
            try {
                if (choice == 1) {
                    String vehicle = InputHelper.readText("Vehicle ID: ");
                    String description = InputHelper.readText("What is the problem: ");
                    service.addJob(vehicle, description);
                    System.out.println("Job saved.");
                } else if (choice == 2) {
                    InputHelper.printList("Jobs", service.getJobs());
                } else if (choice == 3) {
                    String id = InputHelper.readText("Job ID: ");
                    double cost = InputHelper.readDouble("Final cost (UGX): ");
                    service.closeJob(id, cost);
                    System.out.println("Job closed.");
                } else if (choice == 4) {
                    service.removeJob(InputHelper.readText("Job ID: "));
                    System.out.println("Job removed.");
                } else if (choice == 5) {
                    String vehicle = InputHelper.readText("Vehicle ID: ");
                    double litres = InputHelper.readDouble("Litres: ");
                    double cost = InputHelper.readDouble("Cost (UGX): ");
                    service.addFuel(vehicle, litres, cost);
                    System.out.println("Fuel log saved.");
                } else if (choice == 6) {
                    InputHelper.printList("Fuel logs", service.getFuelLogs());
                } else if (choice == 7) {
                    InputHelper.printList("Most expensive first", service.sortedByCost());
                } else if (choice == 8) {
                    ArrayList<BaseRecord> all = new ArrayList<BaseRecord>();
                    all.addAll(service.getJobs());
                    all.addAll(service.getFuelLogs());
                    for (int i = 0; i < all.size(); i++) {
                        System.out.println(all.get(i).describe());
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
