package minijava;

import minijava.symboltable.ClassRecord;

public class Type {
        private ClassRecord c;
        private String recordType;

        // Construct a type of non-object kind.
        public Type(String recordType) {
            this.recordType = recordType;
        }

        // Construct a type of object kind.
        public Type(ClassRecord c) {
            this.recordType = "class";
            this.c = c;
        }

        // Accessors to check the kind/object of the type:

        public boolean isIntArray() {
            return this.recordType.equals("int[]");
        }

        public boolean isBoolean() {
            return this.recordType.equals("boolean");
        }

        public boolean isInt() {
            return this.recordType.equals("int");
        }

        public boolean isObject() {
            return this.recordType.equals("class");
        }

        public String getRecordType() {
            return recordType;
        }

        public ClassRecord getObject() {
            if (!this.recordType.equals("class")) {
                return null;
            }
            return this.c;
        }
}
