package minijava;

import minijava.symboltable.*;

public class ScopeCheckingStatements extends MiniJavaGrammarBaseListener {
    private SymbolTable symbolTable;
    private ClassRecord currentClass;
    private MethodRecord currentMethod;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public ScopeCheckingStatements(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void exitProgram(MiniJavaGrammarParser.ProgramContext ctx) {
        symbolTable.printSymbolTable();
    }
    //
    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        String id = ctx.ID(0).toString();
        currentClass = symbolTable.findClass(id);
    }

    //
    @Override
    public void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        currentClass = null;
    }
    //
    @Override
    public void enterVardecl(MiniJavaGrammarParser.VardeclContext ctx) {
        String id = ctx.ID().toString();
        if (ctx.type().ID() != null) {
            String objectType = ctx.type().ID().toString();
            if (symbolTable.getProgram().getClasses().get(objectType) == null) {
                printError("ERROR: Object doesn't exist");
            }
        }
    }

    @Override
    public void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        currentMethod = currentClass.getMethods().get(ctx.ID().toString());
    }

    //
    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        currentMethod = null;
    }

    //
    @Override
    public void enterFormallist(MiniJavaGrammarParser.FormallistContext ctx) {
        String id = ctx.ID().toString();
        String type = SymbolTableBuilder.getVarType(ctx.type());
        VarRecord v = currentClass.getGlobalVars().get(id);
        if (v != null) {
            if (v.getType().equals(type)) {
                printError("ERROR: Cannot use global variable within parameter");
            }
        }

    }

    @Override
    public void enterAssignStmt(MiniJavaGrammarParser.AssignStmtContext ctx) {
        if (ctx.ID() != null) {
            String varName = ctx.ID().toString();
            if (currentMethod.getLocalVars().get(varName) == null && currentMethod.getParameters().get(varName) == null) {
                if (currentClass.getGlobalVars().get(varName) == null) {
                    printError("ERROR: Variable is not in scope");
                }
            }
        }
    }

    public void enterArrAssignStmt(MiniJavaGrammarParser.ArrAssignStmtContext ctx) {
        if (ctx.ID() != null) {
            String varName = ctx.ID().toString();
            if (currentMethod.getLocalVars().get(varName) == null && currentMethod.getParameters().get(varName) == null) {
                if (currentClass.getGlobalVars().get(varName) == null) {
                    printError("ERROR: int[] Variable is not in scope");
                }
            }
        }
    }

    @Override
    public void exitVarExpr(MiniJavaGrammarParser.VarExprContext ctx) {
        String varName = ctx.ID().toString();
        if (currentMethod.getLocalVars().get(varName) == null && currentMethod.getParameters().get(varName) == null) {
            if (currentClass.getGlobalVars().get(varName) == null) {
                printError("ERROR: Variable in expression is not in scope");
            }
        }
    }

    @Override
    public void exitNewObjExpr(MiniJavaGrammarParser.NewObjExprContext ctx) {
        if (symbolTable.findClass(ctx.ID().toString()) == null) {
            printError("ERROR: Object does not have class declaration");
        }
    }
}


