package minijava.symboltable;
import java.util.Map;

public class SymbolTable {
	private ProgramRecord program;

	/**
	 * Constructor for the Symbol Table
	 */
	public SymbolTable() {
		program = new ProgramRecord("Prog", "Program");
	}

	/**
	 * Returns the program record
	 * @return The program record
	 */
	public ProgramRecord getProgram() {
		return program;
	}


	/**
	 * Finds and returns a class from the symbol table
	 * @param id The class's identifier
	 * @return The class record
	 */
	public ClassRecord findClass(String id) {
		return program.getClasses().get(id);
	}

	/**
	 * Prints the symbol table
	 */
	public void printSymbolTable(){
		Map<String, ClassRecord> allClasses = program.getClasses();
		for (Map.Entry<String, ClassRecord> c : allClasses.entrySet()) {
			Map<String, MethodRecord> allMethods = c.getValue().getMethods();
			Map<String, VarRecord> allGlobalVars = c.getValue().getGlobalVars();
			System.out.println("LEVEL 1: ClASS  " +  "TYPE: " + c.getValue().getType() + "\t IDENTITY: " + c.getValue().getId());
			for (Map.Entry<String,VarRecord> g : allGlobalVars.entrySet()) {
				System.out.println("|		 LEVEL 2: GLOBAL VARIABLE  " + "TYPE: " + g.getValue().getType() +  "\t IDENTITY: " + g.getValue().getId());
			}
			for (Map.Entry<String,MethodRecord> m : allMethods.entrySet()) {
				Map<String, VarRecord> allParams = m.getValue().getParameters();
				Map<String, VarRecord> allLocalVars = m.getValue().getLocalVars();
				System.out.println("|		 LEVEL 2: METHOD           " + "TYPE: " + m.getValue().getType() + "\t IDENTITY: " + m.getValue().getId());
				for (Map.Entry<String, VarRecord> p : allParams.entrySet()) {
					System.out.println("|	 	 |		LEVEL 3 PARAMETER:       " + "TYPE: " + p.getValue().getType() + "\t IDENTITY : " + p.getValue().getId());
				}
				for (Map.Entry<String, VarRecord> l : allLocalVars.entrySet()) {
					System.out.println("|	 	 |		LEVEL 3 LOCAL VARIABLE:  " + "TYPE: " + l.getValue().getType() +  "\t IDENTITY : " + l.getValue().getId());
				}
			}
		}
	}
}