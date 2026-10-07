package ug.ac.vu.g13.fees;

import java.util.ArrayList;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InputHelper;

/**
 * @author Member 6
 */
public class FeesMenu {
    private FeeService service;

    public FeesMenu(FeeService service) {
        this.service = service;
    }

    public void run() {
        String[] options = {"Make invoice for an enrolment", "List invoices", "Remove invoice", "Record payment",
                "List payments", "Report: biggest balance first", "Overview"};
        while (true) {
            int choice = InputHelper.menu("FEES", options);
            if (choice == 0) {
                return;
            }
            try {
                if (choice == 1) {
                    service.addInvoice(InputHelper.readText("Enrolment ID: "));
                    System.out.println("Invoice saved.");
                } else if (choice == 2) {
                    InputHelper.printList("Invoices", service.getInvoices());
                } else if (choice == 3) {
                    service.removeInvoice(InputHelper.readText("Invoice ID: "));
                    System.out.println("Invoice removed.");
                } else if (choice == 4) {
                    String id = InputHelper.readText("Invoice ID: ");
                    double amount = InputHelper.readDouble("Amount (UGX): ");
                    service.addPayment(id, amount);
                    System.out.println("Payment saved.");
                } else if (choice == 5) {
                    InputHelper.printList("Payments", service.getPayments());
                } else if (choice == 6) {
                    InputHelper.printList("Biggest balance first", service.sortedByBalance());
                } else if (choice == 7) {
                    ArrayList<BaseRecord> all = new ArrayList<BaseRecord>();
                    all.addAll(service.getInvoices());
                    all.addAll(service.getPayments());
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
