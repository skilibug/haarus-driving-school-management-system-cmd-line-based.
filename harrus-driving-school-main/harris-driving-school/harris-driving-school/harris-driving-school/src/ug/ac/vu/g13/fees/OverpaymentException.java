package ug.ac.vu.g13.fees;

/**
 * @author Member 6
 */
public class OverpaymentException extends RuntimeException {
    public OverpaymentException(String message) {
        super(message);
    }
}
