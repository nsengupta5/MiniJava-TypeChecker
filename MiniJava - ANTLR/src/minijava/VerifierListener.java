package minijava;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import minijava.symboltable.*;

import java.util.Collections;
import java.util.Map;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

public class VerifierListener extends MiniJavaGrammarBaseListener {
    private SymbolTable symbolTable;
    private boolean debugging = true;
    private Map<String, ClassRecord> classes;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }

    public VerifierListener(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        classes = symbolTable.getProgram().getClasses();
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

    //
    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        String id = ctx.ID(0).toString();
        String type = ctx.CLASS().toString();
        ClassRecord currClass = symbolTable.getProgram().getClasses().get(id);
        if (ctx.EXTENDS() != null) {
            String parentClassId = ctx.ID(1).toString();
        }
    }

    //
    @Override
    public void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {

    }
    //
    @Override
    public void enterVardecl(MiniJavaGrammarParser.VardeclContext ctx) {

    }

    //
    @Override
    public void exitVardecl(MiniJavaGrammarParser.VardeclContext ctx) {
    }

    //
    @Override
    public void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {

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
       if (debugging) System.out.println(ctx.ID());
       String varType = "";
       if (ctx.ID() != null) {
           VarRecord v = findVariable(ctx.ID().toString());
           if (v != null) {
              String vType = v.getType();
              if (vType.equals("int")) {
                  if (ctx.expr(0).INTEGER() == null) {
                     printError("Invalid assignment to variable " + ctx.ID());
                  }
              }
           }
       }
    }


    @Override
    public void exitExpr(MiniJavaGrammarParser.ExprContext ctx) {
        //  System.out.println("exitExpr");
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


