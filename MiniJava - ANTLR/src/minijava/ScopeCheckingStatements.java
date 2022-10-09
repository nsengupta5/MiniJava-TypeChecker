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

    //
    @Override
    public void exitVardecl(MiniJavaGrammarParser.VardeclContext ctx) {
    }

    //
    @Override
    public void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        currentMethod = symbolTable.findMethod(ctx.ID().toString());
    }

    //
    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        currentMethod = null;
    }

    //
    @Override
    public void enterFormallist(MiniJavaGrammarParser.FormallistContext ctx) {
        String id, type;
        id = ctx.ID().toString();
        if (ctx.type().BOOLEAN() != null) {
            type = ctx.type().BOOLEAN().toString();
        }
        else if (ctx.type().LSQUARE() != null) {
            type = "int[]";
        }
        else if (ctx.type().INT() != null) {
            type = ctx.type().INT().toString();
        }
        else {
            type = ctx.type().ID().toString();
        }
        VarRecord v = currentClass.getGlobalVars().get(id);
        if (v != null) {
            if (v.getType().equals(type)) {
                printError("ERROR: Cannot use global variable within parameter");
            }
        }

    }

    //
    @Override
    public void exitFormallist(MiniJavaGrammarParser.FormallistContext ctx) {

    }

    //
    @Override
    public void enterFormalrest(MiniJavaGrammarParser.FormalrestContext ctx) {

    }


    //
    @Override
    public void exitFormalrest(MiniJavaGrammarParser.FormalrestContext ctx) {

    }

    //
    @Override
    public void enterType(MiniJavaGrammarParser.TypeContext ctx) {

    }

    @Override
    public void exitType(MiniJavaGrammarParser.TypeContext ctx) {
        // System.out.println("exitType");
    }
//


    @Override
    public void enterStatement(MiniJavaGrammarParser.StatementContext ctx) {
        if (ctx.ID() != null) {
            String varName = ctx.ID().toString();
            if (currentMethod.getLocalVars().get(varName) == null && currentMethod.getParameters().get(varName) == null) {
                if (currentClass.getGlobalVars().get(varName) == null) {
                    printError("ERROR: Variable is not in scope");
                }
            }
        }
    }

    @Override
    public void exitExpr(MiniJavaGrammarParser.ExprContext ctx) {
        if (ctx.ID() != null && ctx.LPAREN() == null && ctx.RPAREN() == null) {
            String varName = ctx.ID().toString();
            if (currentMethod.getLocalVars().get(varName) == null && currentMethod.getParameters().get(varName) == null) {
                if (currentClass.getGlobalVars().get(varName) == null) {
                    printError("ERROR: Variable is not in scope");
                }
            }
        }

        if (ctx.ID() != null && ctx.NEW() != null) {
            if (symbolTable.findClass(ctx.ID().toString()) == null) {
                printError("ERROR: Object does not have class declaration");
            }
        }

        if (ctx.ID() != null && ctx.DOT() != null) {
            if (ctx.expr(0).THIS() != null) {
                if (currentClass.getMethods().get(ctx.ID().toString()) == null) {
                   printError("ERROR: Current class does not contain given method");
                }
            }
            else {
                if (ctx.expr(0).ID() != null)  {
                    ClassRecord c = symbolTable.findClass(ctx.expr(0).ID().toString());
                    if (c != null && c.getMethods().get(ctx.ID().toString()) == null) {
                        printError("Object class does not contain given method");
                    }
                }
            }
        }

    }

    @Override
    public void enterOp(MiniJavaGrammarParser.OpContext ctx) {
        // System.out.println("enterOp");
    }

    @Override
    public void exitOp(MiniJavaGrammarParser.OpContext ctx) {
        // System.out.println("exitOp");
    }

    @Override
    public void enterExprlist(MiniJavaGrammarParser.ExprlistContext ctx) {
        //System.out.println("enterExprlist");
    }

    @Override
    public void exitExprlist(MiniJavaGrammarParser.ExprlistContext ctx) {
        //System.out.println("exitExprList");
    }

    @Override
    public void enterExprrest(MiniJavaGrammarParser.ExprrestContext ctx) {
        //System.out.println("enterExprrest");
    }

    @Override
    public void exitExprrest(MiniJavaGrammarParser.ExprrestContext ctx) {
        //System.out.println("exitExprrest");
    }
}


