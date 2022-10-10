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

	public Scope getCurrentScope() {
		return scopes.get(currScopeIndex);
	}

	public ClassRecord findClass(String id) {
		return program.getClasses().get(id);
	}

	public void printSymbolTable() {
		Map<String, ClassRecord> allClasses = program.getClasses();
		for (Map.Entry<String, ClassRecord> c : allClasses.entrySet()) {
			Map<String, MethodRecord> allMethods = c.getValue().getMethods();
			Map<String, VarRecord> allGlobalVars = c.getValue().getGlobalVars();
			System.out.println("LEVEL 1: ClASS  " +  "TYPE: " + c.getValue().getType() + " IDENTITY: " + c.getValue().getId());
			for (Map.Entry<String,VarRecord> g : allGlobalVars.entrySet()) {
				System.out.println("		LEVEL 2: GLOBAL VARIABLE  " + "TYPE: " + g.getValue().getType() +  " IDENTITY: " + g.getValue().getId());
			}
			for (Map.Entry<String,MethodRecord> m : allMethods.entrySet()) {
				Map<String, VarRecord> allParams = m.getValue().getParameters();
				Map<String, VarRecord> allLocalVars = m.getValue().getLocalVars();
				System.out.println("		LEVEL 2: METHOD           " + "TYPE: " + m.getValue().getType() + " IDENTITY: " + m.getValue().getId());
				for (Map.Entry<String, VarRecord> p : allParams.entrySet()) {
					System.out.println("				LEVEL 3 PARAMETER:       " + "TYPE: " + p.getValue().getType() + " IDENTITY : " + p.getValue().getId());
				}
				for (Map.Entry<String, VarRecord> l : allLocalVars.entrySet()) {
					System.out.println("				LEVEL 3 LOCAL VARIABLE:  " + "TYPE: " + l.getValue().getType() +  " IDENTITY : " + l.getValue().getId());
				}
			}
		}
	}
}

