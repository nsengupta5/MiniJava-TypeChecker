package minijava.symboltable;

import java.util.Map;
import java.util.HashMap;

public class Program extends Record {
    private Map<String, ClassRecord> classes;

    public Program(String id, String type) {
        super(id, type);
        classes = new HashMap<>();
    }

    public Map<String, ClassRecord> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, ClassRecord> newClasses) {
        classes = newClasses;
    }


    public void pushClass(String key, ClassRecord newClass) {
        classes.put(key, newClass);
    }
}
