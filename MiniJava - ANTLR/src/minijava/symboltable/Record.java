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

	/**
	 * Sets a new type for the record
	 * @param newType The new type
	 */
	public void setType(String newType) {
		this.type = newType;
	}

	/**
	 * Sets a new identifier for the record
	 * @param newId The new identfier
	 */
	public void setId(String newId) {
		this.id = newId;
	}

	/**
	 * Returns the record type
	 * @return The record type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the record identifier
	 * @return The record identifier
	 */
	public String getId() {
		return id;
	}

}
