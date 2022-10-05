package minijava.symboltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SymbolTable {
	private List<Scope> scopes;
	private int currScopeIndex;

	public SymbolTable() {
		this.scopes = new ArrayList<>();
		this.currScopeIndex = -1;
	}

	public void pushScope(Scope newScope) {
		scopes.add(newScope);
		currScopeIndex++;
	}

	public Scope popScope() {
		if (scopes.isEmpty()) {
			return null;
		}

		return scopes.remove(currScopeIndex--);
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
}

