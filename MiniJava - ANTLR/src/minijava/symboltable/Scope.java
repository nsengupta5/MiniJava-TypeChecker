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

	public Scope getParent() {
		return parent;
	}

	public void setScopeClass(ClassRecord scopeClass) {
		this.scopeClass = scopeClass;
	}

	public ClassRecord getScopeClass() {
		return scopeClass;
	}

	public void pushRecord(String key, Record record) {
		records.put(key, record);
	}

	public Map<String, Record> getRecords() {
		return records;
	}

	public void setRecords(Map<String, Record> newRecords) {
		records = newRecords;
	}

	public String toString() {
		if (parent == null) {
			return this.getId() + " " + this.getType() + "\n";
		}
		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<String, Record>> itr = records.entrySet().iterator();
		Map.Entry<String, Record> entry = null;
		while (itr.hasNext()) {
			entry = itr.next();
			sb.append(this.getId() + " " + this.getType() + " " + entry.getValue().getType() + " " + this.parent.getId());
			sb.append("\n");
		}
		return sb.toString();
	}
}
