package minijava.symboltable;

import java.util.Map;
import java.util.HashMap;

public class ProgramRecord extends Record {
    private Map<String, ClassRecord> classes;

    /**
     * Constructor for the Program class
     * @param id The program's identifier
     * @param type The program's type
     */
    public ProgramRecord(String id, String type) {
        super(id, type);
        classes = new HashMap<>();
    }

    /**
     * Returns the program's classes
     * @return A hashmap of the program's classes
     */
    public Map<String, ClassRecord> getClasses() {
        return classes;
    }

    /**
     * Adds a class to the program
     * @param key The new class's identifier
     * @param newClass The new class
     */
    public void pushClass(String key, ClassRecord newClass) {
        classes.put(key, newClass);
    }

}
