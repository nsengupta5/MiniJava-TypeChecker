package minijava;

import minijava.symboltable.*;

public class SymbolTableBuilder extends MiniJavaGrammarBaseListener {
    private MiniJavaGrammarParser parser;
    private SymbolTable symbolTable;
    private ClassRecord currClass;
    private MethodRecord currMethod;

    private boolean debugging = false;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }

    public String getVarType(MiniJavaGrammarParser.TypeContext ctx) {
        if (ctx.BOOLEAN() != null) {
            return ctx.BOOLEAN().toString();
        }
        else if (ctx.LSQUARE() != null) {
            return "int[]";
        }
        else if (ctx.INT() != null) {
            return ctx.INT().toString();
        }
        else {
            return ctx.ID().toString();
        }
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public SymbolTableBuilder(MiniJavaGrammarParser parser) {
        symbolTable = new SymbolTable();
        this.parser = parser;
    }

    @Override
    public void enterProgram(MiniJavaGrammarParser.ProgramContext ctx) {
        // create a scope for the program
        if (debugging) System.out.println("Entered program");
        symbolTable.pushScope(new Scope("prog", "program"));
    }


    @Override
    public void exitProgram(MiniJavaGrammarParser.ProgramContext ctx) {
        if (debugging) System.out.println("Exited program");
        symbolTable.popScope();
    }

    @Override
    public void enterMainclass(MiniJavaGrammarParser.MainclassContext ctx) {
        if (debugging) System.out.println("Entered main class");
        String id = ctx.ID(0).toString();
        String type = ctx.CLASS().toString();
        ClassRecord mainClass = new ClassRecord(id, type);
        symbolTable.addClassToProgram(id, mainClass);
        currClass = mainClass;
        Scope mainScope = new Scope(id, "class");
        mainScope.setParent(symbolTable.getCurrentScope());
        mainScope.setScopeClass(currClass);
        symbolTable.pushScope(mainScope);
        symbolTable.addRecord(id, currClass);
    }

    @Override
    public void exitMainclass(MiniJavaGrammarParser.MainclassContext ctx) {
        if (debugging) System.out.println("Exited main class");
        symbolTable.popScope();
    }

    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        if (debugging) System.out.println("Entered class declaration");
        String id = ctx.ID(0).toString();
        String type = ctx.CLASS().toString();
        if (symbolTable.getProgram().getClasses().get(id) != null) {
            printError("ERROR: Class name already exists");
        }
        ClassRecord newClass = new ClassRecord(id, type);
        if (ctx.EXTENDS() != null) {
            newClass.setParentClass(ctx.ID(1).toString());
        }
        symbolTable.addClassToProgram(id, newClass);
        currClass = newClass;
        Scope classScope = new Scope(id, "class");
        classScope.setParent(symbolTable.getCurrentScope());
        classScope.setScopeClass(currClass);
        symbolTable.pushScope(classScope);
        symbolTable.addRecord(id, currClass);
    }

    @Override
    public void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        if (debugging) System.out.println("Exited class declaration");
        symbolTable.popScope();
    }

    @Override
    public void enterVardecl(MiniJavaGrammarParser.VardeclContext ctx) {
        if (debugging) System.out.println("Entered variable declaration");
        String id = ctx.ID().toString();
        String type = getVarType(ctx.type());
        VarRecord newVar = new VarRecord(id, type);
        if (symbolTable.getCurrentScope().getType().equals("method")) {
            if (currMethod.getLocalVars().get(id) == null && currMethod.getParameters().get(id) == null) {
                currMethod.pushLocalVar(id, newVar);
            }
            else {
                printError("ERROR: Variable already declared within method");
            }
        }
        else {
            if (currClass.getGlobalVars().get(id) == null) {
                currClass.pushGlobalVar(id, newVar);
            }
            else {
                printError("ERROR: Global variable already declared within class");
            }
        }
        Scope varScope = new Scope(id, "variable");
        varScope.setParent(symbolTable.getCurrentScope());
        symbolTable.pushScope(varScope);
        symbolTable.addRecord(id, newVar);
    }

    @Override
    public void exitVardecl(MiniJavaGrammarParser.VardeclContext ctx) {
        if (debugging) System.out.println("Exited variable declaration");
        symbolTable.popScope();
    }

    @Override
    public void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        if (debugging) System.out.println("Entered method declaration");
        String id = ctx.ID().toString();
        String type = getVarType(ctx.type());
        if (currClass.getMethods().get(id) != null) {
            printError("ERROR: Method already defined within class");
        }
        currMethod = new MethodRecord(id, type);
        currClass.pushMethod(id, currMethod);
        Scope methodScope = new Scope(id, "method");
        methodScope.setParent(symbolTable.getCurrentScope());
        methodScope.setScopeClass(currClass);
        symbolTable.pushScope(methodScope);
        symbolTable.addRecord(id, currMethod);
    }

    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        if (debugging) System.out.println("Exited method declaration");
        symbolTable.popScope();
    }

    @Override
    public void enterFormallist(MiniJavaGrammarParser.FormallistContext ctx) {
        if (debugging) System.out.println("Entered formal list");
        String id = ctx.ID().toString();
        String type = getVarType(ctx.type());
        if (currMethod.getParameters().get(id) != null) {
            printError("Parameter already exists");
        }
        VarRecord newVar = new VarRecord(id, type);
        if (symbolTable.findClass(newVar.getType()) != null) {
            currMethod.pushParameter(id, newVar);
        }
        else {
            currMethod.pushParameter(id, newVar);
        }
        Scope paramScope = new Scope(id, "param");
        paramScope.setParent(symbolTable.getCurrentScope());
        symbolTable.pushScope(paramScope);
        symbolTable.addRecord(id, newVar);
    }

    @Override
    public void exitFormallist(MiniJavaGrammarParser.FormallistContext ctx) {
        if (debugging) System.out.println("Exited formal list");
        symbolTable.popScope();
    }

    @Override
    public void enterFormalrest(MiniJavaGrammarParser.FormalrestContext ctx) {
        if (debugging) System.out.println("Entered formal list");
        String id = ctx.ID().toString();
        String type = getVarType(ctx.type());
        if (currMethod.getParameters().get(id) != null) {
            printError("ERROR: Parameter already exists");
        }
        VarRecord newVar = new VarRecord(id, type);
        currMethod.pushParameter(id, newVar);
        Scope paramScope = new Scope(id, "param");
        paramScope.setParent(symbolTable.getCurrentScope());
        symbolTable.pushScope(paramScope);
        symbolTable.addRecord(id, newVar);
    }
    @Override
    public void exitFormalrest(MiniJavaGrammarParser.FormalrestContext ctx) {
        if (debugging) System.out.println("Exited Formal Rest");
        symbolTable.popScope();
    }
}


