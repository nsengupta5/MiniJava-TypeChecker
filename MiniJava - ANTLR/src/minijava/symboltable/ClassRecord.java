package minijava.symboltable;

import java.util.List;
import java.util.ArrayList;

public class ClassRecord extends Record {
	private List<MethodRecord> methods;
	private List<VarRecord> localVars;

	public ClassRecord(String id, String type) {
		super(id, type);
		methods = new ArrayList<>();
		localVars = new ArrayList<>();
	}

	public List<MethodRecord> getMethods() {
		return this.methods;
	}

	public List<VarRecord> getLocalVars() {
		return this.localVars;
	}

	public void setMethods(List<MethodRecord> newMethods) {
		methods = newMethods;
	}

	public void setLocalVars(List<VarRecord> newLocalVars) {
		localVars = newLocalVars;
	}
}
