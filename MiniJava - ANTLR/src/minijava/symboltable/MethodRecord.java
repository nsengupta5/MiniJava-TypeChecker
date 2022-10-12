package minijava.symboltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MethodRecord extends Record {
	private Map<String, VarRecord> parameters;
	private Map<String, VarRecord> localVars;
	private List<String> paramTypes;

	/**
	 * Constructor for method records
	 * @param id The method's identifier
	 * @param type The method's return type
	 */
	public MethodRecord(String id, String type) {
		super(id, type);
		parameters = new HashMap<>();
		localVars = new HashMap<>();
		paramTypes = new ArrayList<>();
	}

	/**
	 * Gets method's parameter variables
	 * @return Method's parameter variables
	 */
	public Map<String, VarRecord> getParameters() {
		return parameters;
	}

	/**
	 * Gets method's local variables
	 * @return Method's local variables
	 */
	public Map<String, VarRecord> getLocalVars() {
		return localVars;
	}

	/**
	 * Adds a parameter variable to the method's parameter list
	 * @param key The variable's identifier
	 * @param newParam The new parameter
	 */
	public void pushParameter(String key, VarRecord newParam) {
		parameters.put(key, newParam);
		// Adds the type of the parameter in the correct order
		if (newParam.getType().equals("int") || newParam.getType().equals("int[]") || newParam.getType().equals("boolean")) {
			paramTypes.add(newParam.getType());
		}
		else {
			paramTypes.add(newParam.getType());
		}
	}

	/**
	 * Gets the parameter types in order
	 * @return The method's parameter types
	 */
	public List<String> getParamTypes() {
		return paramTypes;
	}

	/**
	 * Add a local variable to the method's local variable map
	 * @param key The variable's identifier
	 * @param newLocalVar The new local variable
	 */
	public void pushLocalVar(String key, VarRecord newLocalVar) {
		localVars.put(key, newLocalVar);
	}
}
