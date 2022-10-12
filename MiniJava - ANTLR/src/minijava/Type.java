package minijava;

import minijava.symboltable.ClassRecord;

public class Type {
        private ClassRecord c;
        private String recordType;

    /**
     * Constructor for Type for non-objects
     * @param recordType The record type
     */
    public Type(String recordType) {
            this.recordType = recordType;
        }

    /**
     * Constructor for Type for objects
     * @param c
     */
    public Type(ClassRecord c) {
            this.recordType = "class";
            this.c = c;
    }

    /**
     * Returns a record's type
     * @return The record's type
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * Returns an object type
     * @return the object class
     */
    public ClassRecord getObject() {
        if (!this.recordType.equals("class")) {
            return null;
        }
        return this.c;
    }
}
