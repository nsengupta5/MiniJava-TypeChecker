package minijava.symboltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SymbolTable {
	private List<Scope> scopes;
	private int currScopeIndex;

	public SymbolTable() {
		this.scopes = new ArrayList<>();
		this.currScopeIndex = 0;
	}

	public void pushScope(Scope newScope) {
		scopes.add(newScope);
		currScopeIndex++;
	}

	public Scope popScope() {
		if (scopes.isEmpty()) {
			System.out.print("No scopes");
		}
		else {
			scopes.remove(currScopeIndex - 1);
		}
	}

	public void addRecord(String key, Record record) {
		scopes.get(currScopeIndex).addRecord(key, record);
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
}

