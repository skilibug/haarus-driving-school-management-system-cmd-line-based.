package ug.ac.vu.g13.maintenance;

/**
 * @author SANGANYA
 */
public class JobAlreadyClosedException extends RuntimeException {
    public JobAlreadyClosedException(String message) {
        super(message);
    }
}
