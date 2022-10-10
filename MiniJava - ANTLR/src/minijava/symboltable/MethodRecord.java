package minijava.symboltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MethodRecord extends Record {
	private Map<String, VarRecord> parameters;
	private Map<String, VarRecord> localVars;
	private List<String> paramTypes;

	public MethodRecord(String id, String type) {
		super(id, type);
		parameters = new HashMap<>();
		localVars = new HashMap<>();
		paramTypes = new ArrayList<>();
	}

	public Map<String, VarRecord> getParameters() {
		return parameters;
	}

	public Map<String, VarRecord> getLocalVars() {
		return localVars;
	}

	public void pushParameter(String key, VarRecord newParam) {
		parameters.put(key, newParam);
		if (newParam.getType().equals("int") || newParam.getType().equals("int[]") || newParam.getType().equals("boolean")) {
			paramTypes.add(newParam.getType());
		}
		else {
			paramTypes.add("class");
		}
	}

	public List<String> getParamTypes() {
		return paramTypes;
	}

	public void pushLocalVar(String key, VarRecord newLocalVar) {
		localVars.put(key, newLocalVar);
	}

	public VarRecord getLocalVar(String id) {
		return localVars.get(id);
	}
}
