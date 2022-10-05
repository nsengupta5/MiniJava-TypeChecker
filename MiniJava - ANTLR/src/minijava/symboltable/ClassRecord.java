package minijava.symboltable;

import java.util.Map;
import java.util.HashMap;

public class ClassRecord extends Record {
	private Map<String, MethodRecord> methods;
	private Map<String, VarRecord> globalVars;

	public ClassRecord(String id, String type) {
		super(id, type);
		methods = new HashMap<>();
		globalVars = new HashMap<>();
	}

	public Map<String, MethodRecord> getMethods() {
		return methods;
	}

	public Map<String, VarRecord> getLocalVars() {
		return globalVars;
	}

	public void setMethods(Map<String, MethodRecord> newMethods) {
		methods = newMethods;
	}

	public void setLocalVars(Map<String, VarRecord> newLocalVars) {
		globalVars = newLocalVars;
	}

	public void pushLocalVar(String key, VarRecord localVar) {
		globalVars.put(key, localVar);
	}

	// Check for overloading method
	public void pushMethod(String key, MethodRecord method) {
		methods.put(key, method);
	}

}
