package ug.ac.vu.g13.lessons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.FileStore;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.instructors.Instructor;
import ug.ac.vu.g13.instructors.InstructorService;
import ug.ac.vu.g13.students.Student;
import ug.ac.vu.g13.students.StudentService;
import ug.ac.vu.g13.vehicles.Vehicle;
import ug.ac.vu.g13.vehicles.VehicleService;

/**
 * @author Member 5
 */
public class LessonService {
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    private StudentService studentService;
    private InstructorService instructorService;
    private VehicleService vehicleService;

    public LessonService(StudentService studentService, InstructorService instructorService,
                         VehicleService vehicleService) {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.vehicleService = vehicleService;
        ArrayList<String> lines = FileStore.readLines("lessons.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                Student s = studentService.findStudent(p[2]);
                Instructor ins = instructorService.findInstructor(p[3]);
                if (p[0].equals("THEORY")) {
                    lessons.add(new TheoryLesson(p[1], s, ins, p[4], p[5], p[6]));
                } else {
                    lessons.add(new PracticalLesson(p[1], s, ins, p[4], p[5], vehicleService.findVehicle(p[6])));
                }
            } catch (Exception e) {
                System.out.println("Skipped a bad line in lessons.txt");
            }
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < lessons.size(); i++) {
            lines.add(lessons.get(i).toFileLine());
        }
        FileStore.writeLines("lessons.txt", lines);
    }

    private void checkClash(Instructor ins, String date, String time) {
        for (int i = 0; i < lessons.size(); i++) {
            Lesson l = lessons.get(i);
            if (l.getInstructor() == ins && l.getDate().equals(date) && l.getTime().equals(time)) {
                throw new LessonClashException("The instructor already has lesson " + l.getId() + " at that time.");
            }
        }
    }

    public void addTheory(String studentId, String instructorId, String date, String time, String topic) {
        Student s = studentService.findStudent(studentId);
        Instructor ins = instructorService.findInstructor(instructorId);
        Lesson l = new TheoryLesson(BaseRecord.nextId("L", lessons), s, ins, date, time, topic);
        checkClash(ins, date, time);
        lessons.add(l);
        save();
    }

    public void addPractical(String studentId, String instructorId, String vehicleId, String date, String time) {
        Student s = studentService.findStudent(studentId);
        Instructor ins = instructorService.findInstructor(instructorId);
        Vehicle v = vehicleService.findVehicle(vehicleId);
        Lesson l = new PracticalLesson(BaseRecord.nextId("L", lessons), s, ins, date, time, v);
        checkClash(ins, date, time);
        lessons.add(l);
        save();
    }

    public Lesson findLesson(String id) {
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getId().equalsIgnoreCase(id)) {
                return lessons.get(i);
            }
        }
        throw new InvalidDataException("No lesson with ID " + id);
    }

    public void removeLesson(String id) {
        lessons.remove(findLesson(id));
        save();
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public ArrayList<Lesson> sortedByDate() {
        ArrayList<Lesson> copy = new ArrayList<Lesson>(lessons);
        Collections.sort(copy, new Comparator<Lesson>() {
            public int compare(Lesson a, Lesson b) {
                return (a.getDate() + a.getTime()).compareTo(b.getDate() + b.getTime());
            }
        });
        return copy;
    }
}
