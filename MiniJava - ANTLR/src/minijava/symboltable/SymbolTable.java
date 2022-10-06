package minijava.symboltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SymbolTable {
	private List<Scope> scopes;
	private int currScopeIndex;
	private Program program;

	public SymbolTable() {
		this.scopes = new ArrayList<>();
		this.currScopeIndex = -1;
		program = new Program("Prog", "Program");
	}

	public void pushScope(Scope newScope) {
		scopes.add(newScope);
		currScopeIndex++;
	}

	public int popScope() {
		scopes.remove(currScopeIndex);
		return currScopeIndex--;
	}

	public void addClassToProgram(String id, ClassRecord newClass) {
		program.pushClass(id, newClass);
	}

	public Program getProgram() {
		return program;
	}

	public void addRecord(String key, Record record) {
		scopes.get(currScopeIndex).pushRecord(key, record);
	}

	public Record getRecord(String key) {
		for (int i = currScopeIndex; i >= 0; i--) {
			Map<String, Record> currScopeRecords = scopes.get(i).getRecords();
			if (currScopeRecords.containsKey(key)) {
				return currScopeRecords.get(key);
			}
		}
		return null;
	}

	public void setScopes(List<Scope> newScopes) {
		scopes = newScopes;
	}

	public List<Scope> getScopes() {
		return scopes;
	}

	public void setCurrentScope(Scope newScope) {
		scopes.remove(currScopeIndex);
		scopes.add(newScope);
	}

	public Scope getCurrentScope() {
		return scopes.get(currScopeIndex);
	}

	public int getCurrScopeIndex() {
		return currScopeIndex;
	}

	public void setCurrScopeIndex(int currScopeIndex) {
		this.currScopeIndex = currScopeIndex;
	}

	public void printSymbolTable() {
		Map<String, ClassRecord> allClasses = program.getClasses();
		for (Map.Entry<String, ClassRecord> c : allClasses.entrySet()) {
			Map<String, MethodRecord> allMethods = c.getValue().getMethods();
			Map<String, VarRecord> allGlobalVars = c.getValue().getGlobalVars();
			System.out.println("CLASS: " + c.getValue().getId() + " " + c.getValue().getType());
			for (Map.Entry<String,VarRecord> g : allGlobalVars.entrySet()) {
				System.out.println("GLOBAL VARIABLE: " + g.getValue().getId() + " " + g.getValue().getType() + " " + c.getValue().getId());
			}
			for (Map.Entry<String,MethodRecord> m : allMethods.entrySet()) {
				Map<String, VarRecord> allParams = m.getValue().getParameters();
				Map<String, VarRecord> allLocalVars = m.getValue().getLocalVars();
				System.out.println("METHOD: " + m.getValue().getId() + " " + m.getValue().getType() + " " + c.getValue().getId());
				for (Map.Entry<String, VarRecord> p : allParams.entrySet()) {
					System.out.println("PARAMETER: : " + p.getValue().getId() + " " + p.getValue().getType() + " " + m.getValue().getId());
				}
				for (Map.Entry<String, VarRecord> l : allLocalVars.entrySet()) {
					System.out.println("LOCAL VARIABLE: " + l.getValue().getId() + " " + l.getValue().getType() + " " + m.getValue().getId());
				}
			}
		}
	}
}

