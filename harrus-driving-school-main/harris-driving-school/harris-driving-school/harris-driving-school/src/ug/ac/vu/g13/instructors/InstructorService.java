package ug.ac.vu.g13.instructors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.FileStore;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Member 2
 */
public class InstructorService {
    private ArrayList<Instructor> instructors = new ArrayList<Instructor>();
    private ArrayList<Shift> shifts = new ArrayList<Shift>();

    public InstructorService() {
        ArrayList<String> lines = FileStore.readLines("instructors.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                instructors.add(new Instructor(p[0], p[1], Integer.parseInt(p[2])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in instructors.txt");
            }
        }
        lines = FileStore.readLines("shifts.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                shifts.add(new Shift(p[0], findInstructor(p[1]), Integer.parseInt(p[2]),
                        Integer.parseInt(p[3]), Integer.parseInt(p[4])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in shifts.txt");
            }
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < instructors.size(); i++) {
            lines.add(instructors.get(i).toFileLine());
        }
        FileStore.writeLines("instructors.txt", lines);
        lines = new ArrayList<String>();
        for (int i = 0; i < shifts.size(); i++) {
            lines.add(shifts.get(i).toFileLine());
        }
        FileStore.writeLines("shifts.txt", lines);
    }

    public void addInstructor(String name, int years) {
        instructors.add(new Instructor(BaseRecord.nextId("I", instructors), name, years));
        save();
    }

    public Instructor findInstructor(String id) {
        for (int i = 0; i < instructors.size(); i++) {
            if (instructors.get(i).getId().equalsIgnoreCase(id)) {
                return instructors.get(i);
            }
        }
        throw new InvalidDataException("No instructor with ID " + id);
    }

    public void removeInstructor(String id) {
        Instructor ins = findInstructor(id);
        for (int i = shifts.size() - 1; i >= 0; i--) {
            if (shifts.get(i).getInstructor() == ins) {
                shifts.remove(i);
            }
        }
        instructors.remove(ins);
        save();
    }

    public void addShift(String instructorId, int day, int start, int end) {
        Instructor ins = findInstructor(instructorId);
        Shift newShift = new Shift(BaseRecord.nextId("H", shifts), ins, day, start, end);
        for (int i = 0; i < shifts.size(); i++) {
            Shift old = shifts.get(i);
            if (old.getInstructor() == ins && old.getDay() == day && start < old.getEndHour() && old.getStartHour() < end) {
                throw new ShiftClashException("This shift overlaps shift " + old.getId());
            }
        }
        shifts.add(newShift);
        save();
    }

    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public ArrayList<Instructor> sortedByExperience() {
        ArrayList<Instructor> copy = new ArrayList<Instructor>(instructors);
        Collections.sort(copy, new Comparator<Instructor>() {
            public int compare(Instructor a, Instructor b) {
                return b.getYears() - a.getYears();
            }
        });
        return copy;
    }
}
