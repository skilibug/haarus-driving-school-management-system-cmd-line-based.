package ug.ac.vu.g13.courses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.FileStore;
import ug.ac.vu.g13.core.InvalidDataException;
import ug.ac.vu.g13.students.StudentService;

/**
 * @author Member 4
 */
public class CourseService {
    private ArrayList<Course> courses = new ArrayList<Course>();
    private ArrayList<Enrollment> enrollments = new ArrayList<Enrollment>();
    private StudentService studentService;

    public CourseService(StudentService studentService) {
        this.studentService = studentService;
        ArrayList<String> lines = FileStore.readLines("courses.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                courses.add(new Course(p[0], p[1], Double.parseDouble(p[2]), Integer.parseInt(p[3])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in courses.txt");
            }
        }
        lines = FileStore.readLines("enrollments.txt");
        for (int i = 0; i < lines.size(); i++) {
            try {
                String[] p = lines.get(i).split("\\|");
                enrollments.add(new Enrollment(p[0], studentService.findStudent(p[1]), findCourse(p[2])));
            } catch (Exception e) {
                System.out.println("Skipped a bad line in enrollments.txt");
            }
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < courses.size(); i++) {
            lines.add(courses.get(i).toFileLine());
        }
        FileStore.writeLines("courses.txt", lines);
        lines = new ArrayList<String>();
        for (int i = 0; i < enrollments.size(); i++) {
            lines.add(enrollments.get(i).toFileLine());
        }
        FileStore.writeLines("enrollments.txt", lines);
    }

    public void addCourse(String name, double fee, int capacity) {
        courses.add(new Course(BaseRecord.nextId("C", courses), name, fee, capacity));
        save();
    }

    public Course findCourse(String id) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId().equalsIgnoreCase(id)) {
                return courses.get(i);
            }
        }
        throw new InvalidDataException("No course with ID " + id);
    }

    public Enrollment findEnrollment(String id) {
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getId().equalsIgnoreCase(id)) {
                return enrollments.get(i);
            }
        }
        throw new InvalidDataException("No enrolment with ID " + id);
    }

    public void removeCourse(String id) {
        Course c = findCourse(id);
        for (int i = enrollments.size() - 1; i >= 0; i--) {
            if (enrollments.get(i).getCourse() == c) {
                enrollments.remove(i);
            }
        }
        courses.remove(c);
        save();
    }

    public int seatsLeft(Course c) {
        int taken = 0;
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getCourse() == c) {
                taken++;
            }
        }
        return c.getCapacity() - taken;
    }

    public void enrol(String studentId, String courseId) {
        Course c = findCourse(courseId);
        if (seatsLeft(c) <= 0) {
            throw new CourseFullException(c.getName() + " is full.");
        }
        enrollments.add(new Enrollment(BaseRecord.nextId("E", enrollments), studentService.findStudent(studentId), c));
        save();
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Enrollment> getEnrollments() {
        return enrollments;
    }

    public ArrayList<Course> sortedByFee() {
        ArrayList<Course> copy = new ArrayList<Course>(courses);
        Collections.sort(copy, new Comparator<Course>() {
            public int compare(Course a, Course b) {
                return Double.compare(a.getFee(), b.getFee());
            }
        });
        return copy;
    }
}
