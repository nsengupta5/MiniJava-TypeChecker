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
    private List<MethodRecord> methods;
    private List<ClassRecord> classes;
    private List<VarRecord> globalVars;
    private List<VarRecord> parameters;
    private List<VarRecord> localVars;
    private boolean debugging = true;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }


    public VerifierListener(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        Program program = symbolTable.getProgram();
    }

    //
    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        String id = ctx.ID(0).toString();
        String type = ctx.CLASS().toString();
        ClassRecord currClass = symbolTable.getProgram().getClasses().get(id);
        if (ctx.EXTENDS() != null) {
            ClassRecord par = currClass.getParentClass();
            if (par.getParentClass() != null) {
                ClassRecord par2 = par.getParentClass();
            }
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


