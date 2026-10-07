package ug.ac.vu.g13.students;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Kefas
 */
public class Permit extends BaseRecord {
    private Student student;
    private String status;

    public Permit(String id, Student student, String status) {
        super(id);
        this.student = student;
        setStatus(status);
    }

    public Student getStudent() {
        return student;
    }

    public void setStatus(String status) {
        if (!status.equals("PENDING") && !status.equals("APPROVED") && !status.equals("REJECTED")) {
            throw new InvalidDataException("Status must be PENDING, APPROVED or REJECTED.");
        }
        this.status = status;
    }

    public String describe() {
        return getId() + " | permit of " + student.getName() + " | " + status;
    }

    public String toFileLine() {
        return getId() + "|" + student.getId() + "|" + status;
    }
}
