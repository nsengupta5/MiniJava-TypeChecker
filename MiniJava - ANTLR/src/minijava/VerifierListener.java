package minijava;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import minijava.symboltable.*;

import java.util.*;

public class VerifierListener extends MiniJavaGrammarBaseListener {
    private SymbolTable symbolTable;
    private boolean debugging = true;
    private Map<String, ClassRecord> classes;
    private Stack<String> typeChecker;
    private ClassRecord currentClass;
    private MethodRecord currentMethod;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }

    public VerifierListener(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        classes = symbolTable.getProgram().getClasses();
        typeChecker = new Stack<>();
    }

    public VarRecord findVariable(String id) {
       for (Map.Entry<String, ClassRecord> c : classes.entrySet()) {
           Map<String, VarRecord> globalVars = c.getValue().getGlobalVars();
           for (Map.Entry<String, VarRecord> g : globalVars.entrySet()) {
               if (g.getValue().getId().equals(id)) {
                   return g.getValue();
               }
           }
           Map<String, MethodRecord> methods = c.getValue().getMethods();
           for (Map.Entry<String, MethodRecord> m : methods.entrySet()) {
               Map<String, VarRecord> params = m.getValue().getParameters();
               for (Map.Entry<String, VarRecord> p : params.entrySet()) {
                   if (p.getValue().getId().equals(id)) {
                       return p.getValue();
                   }
               }
               Map<String, VarRecord> localVars = m.getValue().getLocalVars();
               for (Map.Entry<String, VarRecord> l : localVars.entrySet()) {
                   if (l.getValue().getId().equals(id)) {
                       return l.getValue();
                   }
               }
           }
       }
       return null;
    }

    public MethodRecord findMethod(String id) {
        for (Map.Entry<String, ClassRecord> c : classes.entrySet()) {
            Map<String, MethodRecord> methods = c.getValue().getMethods();
            for (Map.Entry<String, MethodRecord> m : methods.entrySet()) {
               if (m.getValue().getId().equals(id)) {
                   return m.getValue();
               }
            }
        }
        return null;
    }

    public void exitProgram(MiniJavaGrammarParser.ProgramContext ctx) {
        symbolTable.printSymbolTable();
    }
    //
    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        String id = ctx.ID(0).toString();
        currentClass = symbolTable.getProgram().getClasses().get(id);
    }

    //
    @Override
    public void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {

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
        currentMethod = findMethod(ctx.ID().toString());
    }

    //
    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {

    }

    //
    @Override
    public void enterFormallist(MiniJavaGrammarParser.FormallistContext ctx) {

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
            if (symbolTable.getProgram().getClasses().get(ctx.ID().toString()) == null) {
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
                    ClassRecord c = symbolTable.getProgram().getClasses().get(ctx.expr(0).ID().toString());
                    if (c != null && c.getMethods().get(ctx.ID().toString()) == null) {
                        printError("Object class does not contain given method");
                    }
                }
            }
        }

//        if (ctx.op() != null) {
//            String s1 = typeChecker.pop();
//            String s2 = typeChecker.pop();
//            if (ctx.op().PLUS() != null || ctx.op().MUL() != null || ctx.op().MINUS() != null) {
//                if (!(s1.equals("int") && s2.equals("int"))) {
//                    printError("BOTH NOT INTEGERS");
//                }
//            }
//        }
//        if (ctx.INTEGER() != null) {
//            typeChecker.push("int");
//        }
//        else if (ctx.FALSE() != null || ctx.TRUE() != null) {
//            typeChecker.push("bool");
//        }
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


