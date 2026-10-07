package ug.ac.vu.g13.fees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.FileStore;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.courses.CourseService;
import ug.ac.vu.g13.courses.Enrollment;

/**
 * @author Member 6
 */
public class FeeService {
    private ArrayList<Invoice> invoices = new ArrayList<Invoice>();
    private ArrayList<Payment> payments = new ArrayList<Payment>();
    private CourseService courseService;

    public FeeService(CourseService courseService) {
        this.courseService = courseService;
        ArrayList<String> lines = FileStore.readLines("invoices.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                invoices.add(new Invoice(p[0], courseService.findEnrollment(p[1]), Double.parseDouble(p[2]),
                        Double.parseDouble(p[3])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in invoices.txt");
            }
        }
        lines = FileStore.readLines("payments.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                payments.add(new Payment(p[0], findInvoice(p[1]), Double.parseDouble(p[2])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in payments.txt");
            }
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < invoices.size(); i++) {
            lines.add(invoices.get(i).toFileLine());
        }
        FileStore.writeLines("invoices.txt", lines);
        lines = new ArrayList<String>();
        for (int i = 0; i < payments.size(); i++) {
            lines.add(payments.get(i).toFileLine());
        }
        FileStore.writeLines("payments.txt", lines);
    }

    public void addInvoice(String enrollmentId) {
        Enrollment e = courseService.findEnrollment(enrollmentId);
        invoices.add(new Invoice(BaseRecord.nextId("N", invoices), e, e.getCourse().getFee(), 0));
        save();
    }

    public Invoice findInvoice(String id) {
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getId().equalsIgnoreCase(id)) {
                return invoices.get(i);
            }
        }
        throw new InvalidDataException("No invoice with ID " + id);
    }

    public void removeInvoice(String id) {
        Invoice inv = findInvoice(id);
        for (int i = payments.size() - 1; i >= 0; i--) {
            if (payments.get(i).getInvoice() == inv) {
                payments.remove(i);
            }
        }
        invoices.remove(inv);
        save();
    }

    public void addPayment(String invoiceId, double amount) {
        Invoice inv = findInvoice(invoiceId);
        if (amount > inv.getBalance()) {
            throw new OverpaymentException("The balance is only " + inv.getBalance() + " UGX.");
        }
        payments.add(new Payment(BaseRecord.nextId("P", payments), inv, amount));
        inv.setPaid(inv.getPaid() + amount);
        save();
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public ArrayList<Invoice> sortedByBalance() {
        ArrayList<Invoice> copy = new ArrayList<Invoice>(invoices);
        Collections.sort(copy, new Comparator<Invoice>() {
            public int compare(Invoice a, Invoice b) {
                return Double.compare(b.getBalance(), a.getBalance());
            }
        });
        return copy;
    }
}
