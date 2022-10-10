package minijava.symboltable;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Scope extends Record {
	private Scope parent;
	private Map<String, Record> records;
	private ClassRecord scopeClass;

	public Scope(String id, String type) {
		super(id, type);
		this.parent = null;
		this.scopeClass = null;
		records = new HashMap<>();
	}

	public void setParent(Scope parent) {
		this.parent = parent;	
	}

	public void setScopeClass(ClassRecord scopeClass) {
		this.scopeClass = scopeClass;
	}

	public void pushRecord(String key, Record record) {
		records.put(key, record);
	}

	public Map<String, Record> getRecords() {
		return records;
	}
}
