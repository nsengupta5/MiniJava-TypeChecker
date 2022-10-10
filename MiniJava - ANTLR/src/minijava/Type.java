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
