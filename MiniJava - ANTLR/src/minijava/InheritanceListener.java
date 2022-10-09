package minijava;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import minijava.symboltable.*;

import java.util.*;

public class InheritanceListener extends MiniJavaGrammarBaseListener {
    private SymbolTable symbolTable;
    private Map<String, ClassRecord> classes;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public InheritanceListener(SymbolTable symbolTable) {
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

    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        String id = ctx.ID(0).toString();
        ClassRecord currClass = symbolTable.getProgram().getClasses().get(id);
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
    }
//

    @Override
    public void enterStatement(MiniJavaGrammarParser.StatementContext ctx) {
    }

    @Override
    public void exitExpr(MiniJavaGrammarParser.ExprContext ctx) {
    }

    @Override
    public void enterOp(MiniJavaGrammarParser.OpContext ctx) {

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


