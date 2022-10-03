package minijava.symboltable;

import java.util.HashMap;
import java.util.Map;

public class Scope {
	private Map<String, Record> records;

	public Scope() {
		records = new HashMap<>();
	}

	public void addRecord(String key, Record record) {
		records.put(key, record);
	}

	public void setRecords(Map<String, Record> newRecords) {
		records = newRecords;
	}

	public Map<String, Record> getRecords() {
		return records;
	}
}
