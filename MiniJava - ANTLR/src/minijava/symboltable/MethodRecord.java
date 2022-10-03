package minijava.symboltable;

import java.util.List;
import java.util.ArrayList;

public class MethodRecord extends Record {
	private List<VarRecord> parameters;
	private List<VarRecord> localVars;

	public MethodRecord(String id, String type) {
		super(id, type);
		parameters = new ArrayList<>();
		localVars = new ArrayList<>();
	}

	public List<VarRecord> getParameters() {
		return parameters;
	}

	public List<VarRecord> getLocalVars() {
		return localVars;
	}

	public void setParameters(List<VarRecord> newParams) {
		parameters = newParams;
	}

	public void setLocalVars(List<VarRecord> newLocalVars) {
		localVars = newLocalVars;
	}

	public void pushParameter(VarRecord newParam) {
		parameters.add(newParam);
	}

	public void pushLocalVar(VarRecord newLocalVar) {
		localVars.add(newLocalVar);
	}
}
