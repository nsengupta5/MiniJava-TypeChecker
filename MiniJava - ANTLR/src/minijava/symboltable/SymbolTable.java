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

	public ClassRecord findClass(String id) {
		return program.getClasses().get(id);
	}

	public VarRecord findVariable(String id) {
		for (Map.Entry<String, ClassRecord> c : program.getClasses().entrySet()) {
			Map<String, VarRecord> globalVars = c.getValue().getGlobalVars();
			for (Map.Entry<String, VarRecord> g : globalVars.entrySet()) {
				if (g.getValue().getId().equals(id)) {
					return g.getValue();
				}
			}
			Map<String, MethodRecord> methods = c.getValue().getMethods();
			for (Map.Entry<String, MethodRecord> m : methods.entrySet()) {
				Map<String, VarRecord> params = m.getValue().getParameters();
				for (Map.Entry<String, VarRecord> p : params.entrySet()) {
					if (p.getValue().getId().equals(id)) {
						return p.getValue();
					}
				}
				Map<String, VarRecord> localVars = m.getValue().getLocalVars();
				for (Map.Entry<String, VarRecord> l : localVars.entrySet()) {
					if (l.getValue().getId().equals(id)) {
						return l.getValue();
					}
				}
			}
		}
		return null;
	}

	public MethodRecord findMethod(String id) {
		for (Map.Entry<String, ClassRecord> c : program.getClasses().entrySet()) {
			Map<String, MethodRecord> methods = c.getValue().getMethods();
			for (Map.Entry<String, MethodRecord> m : methods.entrySet()) {
				if (m.getValue().getId().equals(id)) {
					return m.getValue();
				}
			}
		}
		return null;
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

