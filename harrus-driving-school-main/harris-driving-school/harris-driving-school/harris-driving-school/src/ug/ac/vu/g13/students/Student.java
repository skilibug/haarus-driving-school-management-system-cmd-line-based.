package ug.ac.vu.g13.students;

import ug.ac.vu.g13.core.BaseRecord;
import ug.ac.vu.g13.core.InvalidDataException;

/**
 * @author Kefas
 */
public class Student extends BaseRecord {
    private String name;
    private String phone;
    private int age;

    public Student(String id, String name, String phone, int age) {
        super(id);
        setName(name);
        setPhone(phone);
        setAge(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 2) {
            throw new InvalidDataException("Name is too short.");
        }
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone.length() != 10) {
            throw new InvalidDataException("Phone must have 10 digits.");
        }
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 16 || age > 90) {
            throw new InvalidDataException("Age must be 16 to 90.");
        }
        this.age = age;
    }

    public String describe() {
        return getId() + " | " + name + " | " + phone + " | age " + age;
    }

    public String toFileLine() {
        return getId() + "|" + name + "|" + phone + "|" + age;
    }
}
