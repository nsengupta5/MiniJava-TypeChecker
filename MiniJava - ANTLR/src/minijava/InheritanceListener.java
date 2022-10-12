package minijava;

import minijava.symboltable.*;

import java.util.*;

public class InheritanceListener extends MiniJavaGrammarBaseListener {
    private SymbolTable symbolTable;

    /**
     * Returns the symbol table
     * @return The symbol table
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    /**
     * Constructor for the Inheritance Listener
     * @param symbolTable Symbol table from the previous pass
     */
    public InheritanceListener(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    /**
     * Gets a superclass's methods and global variables for each class
     * that has a superclass
     * @param ctx the parse tree
     */
    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        String id = ctx.ID(0).toString();
        ClassRecord currClass = symbolTable.findClass(id);
        if (ctx.EXTENDS() != null) {
            String parentClassId = ctx.ID(1).toString();
            List<String> parents = new ArrayList<>();
            // Checks for cyclic inheritance by keeping track of parent sequence
            while (parentClassId != null) {
                if (parents.contains(parentClassId)) {
                    SymbolTableBuilder.printError("ERROR: Cyclic inheritance is not allowed");
                }
                parents.add(parentClassId);
                ClassRecord parent = symbolTable.getProgram().getClasses().get(parentClassId);
                // Adds all superclass's global variables to current class
                parent.getGlobalVars().forEach((k,v) -> {
                    if (currClass.getGlobalVars().get(k) == null) {
                        currClass.pushGlobalVar(k, v);
                    }
                });
                // Adds all superclass's methods to current class
                parent.getMethods().forEach((k,v) -> {
                    if (currClass.getMethods().get(k) == null) {
                        currClass.pushMethod(k, v);
                    }
                });
                parentClassId = parent.getParentClassId();
            }
        }
    }
}