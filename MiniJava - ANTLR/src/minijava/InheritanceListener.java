package minijava;

import minijava.symboltable.*;

import java.util.*;

public class InheritanceListener extends MiniJavaGrammarBaseListener {
    private SymbolTable symbolTable;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public InheritanceListener(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }


    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        String id = ctx.ID(0).toString();
        ClassRecord currClass = symbolTable.findClass(id);
        if (ctx.EXTENDS() != null) {
            String parentClassId = ctx.ID(1).toString();
            List<String> parents = new ArrayList<>();
            while (parentClassId != null) {
                if (parents.contains(parentClassId)) {
                    printError("ERROR: Cyclic inheritance is not allowed");
                }
                parents.add(parentClassId);
                ClassRecord parent = symbolTable.getProgram().getClasses().get(parentClassId);
                parent.getGlobalVars().forEach((k,v) -> {
                    if (currClass.getGlobalVars().get(k) == null) {
                        currClass.pushGlobalVar(k, v);
                    }
                });
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


