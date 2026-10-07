package ug.ac.vu.g13.tests;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 7
 */
public class TestResult extends BaseRecord {
    private TestBooking booking;
    private int score;

    public TestResult(String id, TestBooking booking, int score) {
        super(id);
        if (score < 0 || score > 100) {
            throw new InvalidDataException("Score must be 0 to 100.");
        }
        this.booking = booking;
        this.score = score;
    }

    public TestBooking getBooking() {
        return booking;
    }

    public int getScore() {
        return score;
    }

    public boolean isPassed() {
        return score >= 60;
    }

    public String describe() {
        String result = "FAILED";
        if (isPassed()) {
            result = "PASSED";
        }
        return getId() + " | " + booking.getStudent().getName() + " | " + booking.getType() + " | score " + score
                + " | " + result;
    }

    public String toFileLine() {
        return getId() + "|" + booking.getId() + "|" + score;
    }
}
