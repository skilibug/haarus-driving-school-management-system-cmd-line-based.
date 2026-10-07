package ug.ac.vu.g13.vehicles;

import ug.ac.vu.g13.core.InputHelper;

/**
 * @author Member 3
 */
public class VehiclesMenu {
    private VehicleService service;

    public VehiclesMenu(VehicleService service) {
        this.service = service;
    }

    public void run() {
        String[] options = {"Add car", "Add motorcycle", "List vehicles", "Change kilometres",
                "Remove vehicle", "Report: most kilometres first", "Overview"};
        while (true) {
            int choice = InputHelper.menu("VEHICLES", options);
            if (choice == 0) {
                return;
            }
            try {
                if (choice == 1) {
                    String plate = InputHelper.readText("Plate: ");
                    int km = InputHelper.readInt("Kilometres: ", 0, 5000000);
                    int seats = InputHelper.readInt("Seats: ", 0, 100);
                    service.addCar(plate, km, seats);
                    System.out.println("Car saved.");
                } else if (choice == 2) {
                    String plate = InputHelper.readText("Plate: ");
                    int km = InputHelper.readInt("Kilometres: ", 0, 5000000);
                    int cc = InputHelper.readInt("Engine size (cc): ", 0, 10000);
                    service.addMotorcycle(plate, km, cc);
                    System.out.println("Motorcycle saved.");
                } else if (choice == 3) {
                    InputHelper.printList("Vehicles", service.getVehicles());
                } else if (choice == 4) {
                    Vehicle v = service.findVehicle(InputHelper.readText("Vehicle ID: "));
                    v.setKm(InputHelper.readInt("New kilometres: ", 0, 5000000));
                    service.save();
                    System.out.println("Changed.");
                } else if (choice == 5) {
                    service.removeVehicle(InputHelper.readText("Vehicle ID: "));
                    System.out.println("Vehicle removed.");
                } else if (choice == 6) {
                    InputHelper.printList("By kilometres", service.sortedByKm());
                } else if (choice == 7) {
                    for (int i = 0; i < service.getVehicles().size(); i++) {
                        Vehicle v = service.getVehicles().get(i);
                        System.out.println(v.type() + " " + v.getPlate() + " -> " + v.extraInfo());
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
