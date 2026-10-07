package ug.ac.vu.g13.fees;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.courses.Enrollment;

/**
 * @author Member 6
 */
public class Invoice extends BaseRecord {
    private Enrollment enrollment;
    private double due;
    private double paid;

    public Invoice(String id, Enrollment enrollment, double due, double paid) {
        super(id);
        if (due <= 0 || paid < 0 || paid > due) {
            throw new InvalidDataException("Amounts are not valid.");
        }
        this.enrollment = enrollment;
        this.due = due;
        this.paid = paid;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public double getDue() {
        return due;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        if (paid < 0 || paid > due) {
            throw new InvalidDataException("Paid amount must be between 0 and " + due);
        }
        this.paid = paid;
    }

    public double getBalance() {
        return due - paid;
    }

    public String describe() {
        return getId() + " | " + enrollment.getStudent().getName() + " | due " + due + " | paid " + paid
                + " | balance " + getBalance();
    }

    public String toFileLine() {
        return getId() + "|" + enrollment.getId() + "|" + due + "|" + paid;
    }
}
