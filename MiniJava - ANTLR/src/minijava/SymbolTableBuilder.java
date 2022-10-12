package minijava;

import minijava.symboltable.*;

public class SymbolTableBuilder extends MiniJavaGrammarBaseListener {
    private MiniJavaGrammarParser parser;
    private SymbolTable symbolTable;
    private ClassRecord currClass;
    private MethodRecord currMethod;

    /**
     * Prints an error and exits the program
     * @param error The error string
     */
    public static void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }

    /**
     * Returns the type of variable
     * @param ctx A TypeContext
     * @return The type of variable
     */
    public static String getVarType(MiniJavaGrammarParser.TypeContext ctx) {
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

    /**
     * Returns the symbol table
     * @return The symbol table
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    /**
     * Constructor for the Symbol Table Builder
     * @param parser The MiniJavaGrammarParser
     */
    public SymbolTableBuilder(MiniJavaGrammarParser parser) {
        this.parser = parser;
    }

    /**
     * Creates a new program record in the symbol table
     * @param ctx the parse tree
     */
    @Override
    public void enterProgram(MiniJavaGrammarParser.ProgramContext ctx) {
        symbolTable = new SymbolTable();
    }

    /**
     * Adds the main class to the symbol table and sets the current class to
     * the main class
     * @param ctx the parse tree
     */
    @Override
    public void enterMainclass(MiniJavaGrammarParser.MainclassContext ctx) {
        String id = ctx.ID(0).toString();
        String type = ctx.CLASS().toString();
        ClassRecord mainClass = new ClassRecord(id, type);
        currClass = mainClass;
        symbolTable.getProgram().pushClass(id, mainClass);
    }

    /**
     * Exists the main class and sets the current class to null
     * @param ctx the parse tree
     */
    @Override
    public void exitMainclass(MiniJavaGrammarParser.MainclassContext ctx) {
        currClass = null;
    }

    /**
     * Adds a new class to the symbol table and sets the current class
     * to the new class
     * @param ctx the parse tree
     */
    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        String id = ctx.ID(0).toString();
        String type = ctx.CLASS().toString();
        // Checks the class hasn't already been declared
        if (symbolTable.findClass(id) != null) {
            printError("ERROR: Class name \"" + id + "\" already exists");
        }
        ClassRecord newClass = new ClassRecord(id, type);
        // Sets the superclass for the new class
        if (ctx.EXTENDS() != null) {
            newClass.setParentClass(ctx.ID(1).toString());
        }
        currClass = newClass;
        symbolTable.getProgram().pushClass(id, newClass);
    }

    /**
     * Exits the class declaration and sets the current class to null
     * @param ctx the parse tree
     */
    @Override
    public void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        currClass = null;
    }

    /**
     * Adds a new variable to the symbol table
     * @param ctx the parse tree
     */
    @Override
    public void enterVardecl(MiniJavaGrammarParser.VardeclContext ctx) {
        String id = ctx.ID().toString();
        String type = getVarType(ctx.type());
        VarRecord newVar = new VarRecord(id, type);
        // Checks if the current scope is a method
        if (currMethod != null) {
            // Checks if the current method already has a parameter or local variable of the same variable
            if (currMethod.getLocalVars().get(id) == null && currMethod.getParameters().get(id) == null) {
                currMethod.pushLocalVar(id, newVar);
            }
            else {
                printError("ERROR: Variable \"" + id + "\" already declared within method \"" + currMethod.getId() + "\"");
            }
        }
        else {
            // Checks if current closs has a global variable of the same variable
            if (currClass.getGlobalVars().get(id) == null) {
                currClass.pushGlobalVar(id, newVar);
            }
            else {
                printError("ERROR: Global variable \"" + id + "\" already declared within class \"" + currClass.getId() + "\"" );
            }
        }
    }

    /**
     * Enters a method declaration and sets the current method to the new method
     * @param ctx the parse tree
     */
    @Override
    public void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        String id = ctx.ID().toString();
        String type = getVarType(ctx.type());
        // Checks if method already exists with the class
        if (currClass.getMethods().get(id) != null) {
            printError("ERROR: Method \"" + id + "\" already defined within class \"" + currClass.getId() + "\"");
        }
        currMethod = new MethodRecord(id, type);
        currClass.pushMethod(id, currMethod);
    }

    /**
     * Exists method declaration and sets current method to null
     * @param ctx the parse tree
     */
    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        currMethod = null;
    }

    /**
     * Enter method's formal parameters
     * @param ctx the parse tree
     */
    @Override
    public void enterFormallist(MiniJavaGrammarParser.FormallistContext ctx) {
        String id = ctx.ID().toString();
        String type = getVarType(ctx.type());
        // Checks if current method already contains parameter
        if (currMethod.getParameters().get(id) != null) {
            printError("Parameter \"" + id + "\" already exists in method \"" + currMethod.getId() + "\"");
        }
        VarRecord newVar = new VarRecord(id, type);
        currMethod.pushParameter(id, newVar);
    }

    /**
     * Enter the rest of a method's formal parameters
     * @param ctx the parse tree
     */
    @Override
    public void enterFormalrest(MiniJavaGrammarParser.FormalrestContext ctx) {
        String id = ctx.ID().toString();
        String type = getVarType(ctx.type());
        // Checks if current method already contains parameter
        if (currMethod.getParameters().get(id) != null) {
            printError("Parameter \"" + id + "\" already exists in method \"" + currMethod.getId() + "\"");
        }
        VarRecord newVar = new VarRecord(id, type);
        currMethod.pushParameter(id, newVar);
    }
}


