package minijava;
import minijava.symboltable.*;
public class ScopeCheckingStatements extends MiniJavaGrammarBaseListener {
    private SymbolTable symbolTable;
    private ClassRecord currClass;
    private MethodRecord currMethod;

    /**
     * Returns the symbol table
     * @return The symbol table
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    /**
     * Constructor for the ScopeCheckingStatements pass
     * @param symbolTable The symbol table from the previous pass
     */
    public ScopeCheckingStatements(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    /**
     * Prints the symbol table
     * @param ctx the parse tree
     */
    public void exitProgram(MiniJavaGrammarParser.ProgramContext ctx) {
        symbolTable.printSymbolTable();
    }

    /**
     * Sets the current class
     * @param ctx the parse tree
     */
    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        String id = ctx.ID(0).toString();
        currClass = symbolTable.findClass(id);
    }

    /**
     * Sets the current class to null
     * @param ctx the parse tree
     */
    @Override
    public void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        currClass = null;
    }

    /**
     * Checking variable declarations for object existance
     * @param ctx the parse tree
     */
    @Override
    public void enterVardecl(MiniJavaGrammarParser.VardeclContext ctx) {
        if (ctx.type().ID() != null) {
            String objectType = ctx.type().ID().toString();
            // Checks if object exists
            if (symbolTable.getProgram().getClasses().get(objectType) == null) {
                SymbolTableBuilder.printError("ERROR: Object \"" + objectType + "\" doesn't exist");
            }
        }
    }

    /**
     * Sets the current method
     * @param ctx the parse tree
     */
    @Override
    public void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        currMethod = currClass.getMethods().get(ctx.ID().toString());
    }

    /**
     * Sets the current method to null
     * @param ctx the parse tree
     */
    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        currMethod = null;
    }

    /**
     * Entering formal lists to check if global variables incorrectly exists
     * @param ctx the parse tree
     */
    @Override
    public void enterFormallist(MiniJavaGrammarParser.FormallistContext ctx) {
        String id = ctx.ID().toString();
        String type = SymbolTableBuilder.getVarType(ctx.type());
        VarRecord v = currClass.getGlobalVars().get(id);
        if (v != null) {
            if (v.getType().equals(type)) {
                SymbolTableBuilder.printError("ERROR: Cannot use global variable \"" + id + "\" within parameters");
            }
        }

    }

    /**
     * Checks if variable being assigned is in the current scope
     * @param ctx the parse tree
     */
    @Override
    public void enterAssignStmt(MiniJavaGrammarParser.AssignStmtContext ctx) {
        if (ctx.ID() != null) {
            String varName = ctx.ID().toString();
            if (currMethod.getLocalVars().get(varName) == null && currMethod.getParameters().get(varName) == null) {
                if (currClass.getGlobalVars().get(varName) == null) {
                    SymbolTableBuilder.printError("ERROR: Variable \"" + varName + "\" is not in scope");
                }
            }
        }
    }

    /**
     * Checks if the integer array assignment is in scope
     * @param ctx the parse tree
     */
    public void enterArrAssignStmt(MiniJavaGrammarParser.ArrAssignStmtContext ctx) {
        if (ctx.ID() != null) {
            String varName = ctx.ID().toString();
            if (currMethod.getLocalVars().get(varName) == null && currMethod.getParameters().get(varName) == null) {
                if (currClass.getGlobalVars().get(varName) == null) {
                    SymbolTableBuilder.printError("ERROR: int[] Variable \"" + varName + "\" is not in scope");
                }
            }
        }
    }

    /**
     * Checks if a variable in an expression is in scope
     * @param ctx the parse tree
     */
    @Override
    public void exitVarExpr(MiniJavaGrammarParser.VarExprContext ctx) {
        String varName = ctx.ID().toString();
        if (currMethod.getLocalVars().get(varName) == null && currMethod.getParameters().get(varName) == null) {
            if (currClass.getGlobalVars().get(varName) == null) {
                SymbolTableBuilder.printError("ERROR: Variable \"" + varName + "\" in expression is not in scope");
            }
        }
    }

    /**
     * Checks if object exists and has a corresponding class
     * @param ctx the parse tree
     */
    @Override
    public void exitNewObjExpr(MiniJavaGrammarParser.NewObjExprContext ctx) {
        if (symbolTable.findClass(ctx.ID().toString()) == null) {
            SymbolTableBuilder.printError("ERROR: Object \"" + ctx.ID().toString() + "\" does not have class declaration");
        }
    }
}