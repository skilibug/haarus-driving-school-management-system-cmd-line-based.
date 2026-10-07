package ug.ac.vu.g13.students;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.FileStore;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Kefas
 */
public class StudentService {
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Permit> permits = new ArrayList<Permit>();

    public StudentService() {
        ArrayList<String> lines = FileStore.readLines("students.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                students.add(new Student(p[0], p[1], p[2], Integer.parseInt(p[3])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in students.txt");
            }
        }
        lines = FileStore.readLines("permits.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                permits.add(new Permit(p[0], findStudent(p[1]), p[2]));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in permits.txt");
            }
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < students.size(); i++) {
            lines.add(students.get(i).toFileLine());
        }
        FileStore.writeLines("students.txt", lines);
        lines = new ArrayList<String>();
        for (int i = 0; i < permits.size(); i++) {
            lines.add(permits.get(i).toFileLine());
        }
        FileStore.writeLines("permits.txt", lines);
    }

    public void addStudent(String name, String phone, int age) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getPhone().equals(phone)) {
                throw new DuplicateStudentException("This phone number is already registered.");
            }
        }
        students.add(new Student(BaseRecord.nextId("S", students), name, phone, age));
        save();
    }

    public Student findStudent(String id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equalsIgnoreCase(id)) {
                return students.get(i);
            }
        }
        throw new InvalidDataException("No student with ID " + id);
    }

    public void removeStudent(String id) {
        Student s = findStudent(id);
        for (int i = permits.size() - 1; i >= 0; i--) {
            if (permits.get(i).getStudent() == s) {
                permits.remove(i);
            }
        }
        students.remove(s);
        save();
    }

    public void addPermit(String studentId) {
        Student s = findStudent(studentId);
        permits.add(new Permit(BaseRecord.nextId("A", permits), s, "PENDING"));
        save();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Permit> getPermits() {
        return permits;
    }

    public ArrayList<Student> sortedByName() {
        ArrayList<Student> copy = new ArrayList<Student>(students);
        Collections.sort(copy, new Comparator<Student>() {
            public int compare(Student a, Student b) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });
        return copy;
    }
}
