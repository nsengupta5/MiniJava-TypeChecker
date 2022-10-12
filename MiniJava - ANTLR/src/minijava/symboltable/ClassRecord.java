package minijava.symboltable;

import java.util.Map;
import java.util.HashMap;

public class ClassRecord extends Record {
	private Map<String, MethodRecord> methods;
	private Map<String, VarRecord> globalVars;
	private String parentClassId;

	/**
	 * Constructor for class records
	 * @param id The class's identifier
	 * @param type The class's type
	 */
	public ClassRecord(String id, String type) {
		super(id, type);
		methods = new HashMap<>();
		globalVars = new HashMap<>();
	}

	/**
	 * Sets the superclass of the current class
	 * @param parentClassId The superclass's identifier
	 */
	public void setParentClass(String parentClassId) {
		this.parentClassId = parentClassId;
	}

	/**
	 * Returns the superclass's identifier
	 * @return The superclass's identifier
	 */
	public String getParentClassId() {
		return parentClassId;
	}

	/**
	 * Returns the class's methods
	 * @return A hashmap of the class's methods
	 */
	public Map<String, MethodRecord> getMethods() {
		return methods;
	}

	/**
	 * Returns the class's global variables
	 * @return A hashmap fo the class's global variables
	 */
	public Map<String, VarRecord> getGlobalVars() {
		return globalVars;
	}

	/**
	 * Adds a new global variable to the class
	 * @param key The global variable's identifier
	 * @param localVar The new global variable
	 */
	public void pushGlobalVar(String key, VarRecord localVar) {
		globalVars.put(key, localVar);
	}

	/**
	 * Adds a new method to the class
	 * @param key The method's identifier
	 * @param method The new method
	 */
	public void pushMethod(String key, MethodRecord method) {
		methods.put(key, method);
	}

}
