package minijava.symboltable;

public class Record {
	private String id;
	private String type;

	private Record parent;

	public Record(String id, String type) {
		this.id = id;
		this.type = type;
		this.parent = null;
	}

	public void setType(String newType) {
		this.type = newType;
	}

	public void setId(String newId) {
		this.id = newId;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public Record getParent() {
		return parent;
	}

	public void setParent(Record parent) {
		this.parent = parent;
	}
}
