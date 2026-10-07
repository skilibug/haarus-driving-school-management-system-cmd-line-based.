package ug.ac.vu.g13.fees;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 6
 */
public class Payment extends BaseRecord {
    private Invoice invoice;
    private double amount;

    public Payment(String id, Invoice invoice, double amount) {
        super(id);
        if (amount <= 0) {
            throw new InvalidDataException("Payment must be more than 0.");
        }
        this.invoice = invoice;
        this.amount = amount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public double getAmount() {
        return amount;
    }

    public String describe() {
        return getId() + " | invoice " + invoice.getId() + " | amount " + amount;
    }

    public String toFileLine() {
        return getId() + "|" + invoice.getId() + "|" + amount;
    }
}
