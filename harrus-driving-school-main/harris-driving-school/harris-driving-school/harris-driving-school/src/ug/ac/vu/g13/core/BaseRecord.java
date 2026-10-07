package ug.ac.vu.g13.core;

import java.util.ArrayList;

/**
 * @author Kefas
 */
public abstract class BaseRecord implements Reportable {
    private String id;

    public BaseRecord(String id) {
        if (!id.startsWith("G13-")) {
            throw new InvalidDataException("ID must start with G13-");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract String describe();

    public abstract String toFileLine();

    public String reportLine() {
        return describe();
    }

    public static String nextId(String letter, ArrayList<? extends BaseRecord> list) {
        int biggest = 0;
        for (int i = 0; i < list.size(); i++) {
            int number = Integer.parseInt(list.get(i).getId().substring(5));
            if (number > biggest) {
                biggest = number;
            }
        }
        return "G13-" + letter + String.format("%03d", biggest + 1);
    }
}
