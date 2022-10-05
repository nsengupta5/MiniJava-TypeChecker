package minijava.symboltable;

import java.util.Map;
import java.util.HashMap;

public class MethodRecord extends Record {
	private Map<String, VarRecord> parameters;
	private Map<String, VarRecord> localVars;

	public MethodRecord(String id, String type) {
		super(id, type);
		parameters = new HashMap<>();
		localVars = new HashMap<>();
	}

	public Map<String, VarRecord> getParameters() {
		return parameters;
	}

	public Map<String, VarRecord> getLocalVars() {
		return localVars;
	}

	public void setParameters(Map<String, VarRecord> newParams) {
		parameters = newParams;
	}

	public void setLocalVars(Map<String, VarRecord> newLocalVars) {
		localVars = newLocalVars;
	}

	public void pushParameter(String key, VarRecord newParam) {
		parameters.put(key, newParam);
	}

	public void pushLocalVar(String key, VarRecord newLocalVar) {
		localVars.put(key, newLocalVar);
	}

	public VarRecord getLocalVar(String id) {
		return localVars.get(id);
	}
}
