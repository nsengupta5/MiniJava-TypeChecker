package minijava.symboltable;

/**
 * Base class Record
 */
public class Record {
	private String id;
	private String type;

	/**
	 * Constructor class
	 * @param id The record's identifier
	 * @param type The record's type
	 */
	public Record(String id, String type) {
		this.id = id;
		this.type = type;
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

}
