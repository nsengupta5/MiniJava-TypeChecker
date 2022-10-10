package minijava;

import minijava.symboltable.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class TypeChecker extends MiniJavaGrammarBaseListener {
    private MiniJavaGrammarParser parser;
    private SymbolTable symbolTable;
    private ClassRecord currClass;
    private MethodRecord currMethod;
    private Stack<Type> typeChecker;

    private boolean debugging = false;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public TypeChecker(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        typeChecker = new Stack<>();
    }

    @Override
    public void enterProgram(MiniJavaGrammarParser.ProgramContext ctx) {

    }


    @Override
    public void exitProgram(MiniJavaGrammarParser.ProgramContext ctx) {

    }

    @Override
    public void enterMainclass(MiniJavaGrammarParser.MainclassContext ctx) {

    }

    //
    @Override
    public void exitMainclass(MiniJavaGrammarParser.MainclassContext ctx) {

    }

    //
    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        currClass = symbolTable.findClass(ctx.ID(0).toString());
    }

    //
    @Override
    public void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        currClass = null;
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
        currMethod = symbolTable.findMethod(ctx.ID().toString());
    }

    //
    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        currMethod = null;
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
    public void exitStatement(MiniJavaGrammarParser.StatementContext ctx) {
        if (ctx.IF() != null) {
           String s1 = typeChecker.pop().getRecordType();
           if (!s1.equals("boolean")) {
               System.err.println("ERROR: Conditions of if statements must be of type boolean");
           }
        }

        if (ctx.WHILE() != null) {
            String s1 = typeChecker.pop().getRecordType();
            if (!s1.equals("boolean")) {
                System.err.println("ERROR: Conditions of while statements must be of type boolean");
            }
        }

        if (ctx.SYSTEMOUT() != null) {
            String s1 = typeChecker.pop().getRecordType();
            if (!s1.equals("int")) {
                System.err.println("ERROR: Arguments of Println must be of type integer");
            }
        }

        if (ctx.EQUALS() != null && ctx.LSQUARE() == null) {
            VarRecord v;
            if (currMethod.getLocalVar(ctx.ID().toString()) != null) {
                v = currMethod.getLocalVars().get(ctx.ID().toString());
            }
            else if (currMethod.getParameters().get(ctx.ID().toString()) != null) {
                v = currMethod.getParameters().get(ctx.ID().toString());
            }
            else {
                v = currClass.getGlobalVars().get(ctx.ID().toString());
            }
            String lhs;
            if (symbolTable.findClass(v.getType()) != null) {
                lhs = "class";
            }
            else {
                lhs = v.getType();
            }
            String rhs = typeChecker.pop().getRecordType();
            if (!lhs.equals(rhs)) {
                System.err.println("ERROR: Incompatible types");
            }
        }

        if (ctx.EQUALS() != null && ctx.LSQUARE() != null) {
            String arr = symbolTable.findVariable(ctx.ID().toString()).getType();
            String lhs = typeChecker.pop().getRecordType();
            String index = typeChecker.pop().getRecordType();

            if (!arr.equals("int[]")) {
                System.err.println("ERROR: Variable must be of type int[]");
            }
            if (!lhs.equals("int")) {
                System.err.println("ERROR: Int array element must be assigned to an integer");
            }
            if (!index.equals("int")) {
                System.err.println("ERROR: Index of arrays must be an integer");
            }
        }
    }

    public void enterExpr(MiniJavaGrammarParser.ExprContext ctx) {
        if (ctx.DOT() != null && ctx.LPAREN() != null) {
            typeChecker.push(null);
        }
    }

    @Override
    public void exitExpr(MiniJavaGrammarParser.ExprContext ctx) {
        if (ctx.op() != null) {
            String s1 = typeChecker.pop().getRecordType();
            String s2 = typeChecker.pop().getRecordType();
            if (ctx.op().AND() == null) {
                if (!(s1.equals("int") && s2.equals("int"))) {
                    System.err.println("ERROR: Both not integers");
                }
            }
            else {
                if (!(s1.equals("boolean") && s2.equals("boolean"))) {
                    System.err.println("ERROR: Both not booleans");
                }
            }

            if (ctx.op().LT() != null || ctx.op().AND() != null) {
                typeChecker.push(new Type("boolean"));
            }
            else {
                typeChecker.push(new Type("int"));
            }
        }

        if (ctx.RSQUARE() != null && ctx.LSQUARE() != null && ctx.NEW() == null) {
            String index = typeChecker.pop().getRecordType();
            String arr = typeChecker.pop().getRecordType();
            if (index.equals("int")) {
                if (arr.equals("int[]")) {
                    typeChecker.push(new Type("int"));
                }
                else {
                    System.err.println("ERROR: Array lookup has to be an integer");
                }
            }
            else {
                System.err.println("ERROR: Index has to be an integer");
            }
        }

        if (ctx.LENGTH() != null) {
            String s1 = typeChecker.pop().getRecordType();
            if (s1.equals("int[]")) {
                typeChecker.push(new Type("int"));
            }
            else {
                System.err.println("ERROR: Length can only be applied to type int[]");
            }
        }

        if (ctx.DOT() != null && ctx.ID() != null) {
           Stack<Type> args = new Stack<>();
           while (typeChecker.peek() != null) {
               args.push(typeChecker.pop());
           }

           typeChecker.pop();
           Type methodType = args.pop();
           if (!methodType.getRecordType().equals("class")) {
               System.err.println("ERROR: Only objects can call methods");
               typeChecker.push(new Type("class"));
               return;
           }

           MethodRecord m = methodType.getObject().getMethods().get(ctx.ID().toString());
           List<String> params = m.getParamTypes();
           if (params.size() != args.size()) {
               System.err.println("ERROR: Method call has invalid number of arguments");
               typeChecker.push(new Type("class"));
               return;
           }

           for (String param : params) {
               String t = args.pop().getRecordType();
               if (!t.equals(param)) {
                   System.err.println("ERROR: Argument type not compatible");
               }
           }

           if (symbolTable.findClass(m.getType()) != null) {
               typeChecker.push(new Type(symbolTable.findClass(m.getType())));
           }
           else {
               typeChecker.push(new Type(m.getType()));
           }
        }

        if (ctx.INTEGER() != null) {
            typeChecker.push(new Type("int"));
        }

        if (ctx.FALSE() != null || ctx.TRUE() != null) {
            typeChecker.push(new Type("boolean"));
        }

        if (ctx.ID() != null && ctx.LPAREN() == null) {
            VarRecord v;
            if (currMethod.getLocalVars().get(ctx.ID().toString()) != null) {
                v = currMethod.getLocalVars().get(ctx.ID().toString());
            }
            else if (currMethod.getParameters().get(ctx.ID().toString()) != null) {
                v = currMethod.getParameters().get(ctx.ID().toString());
            }
            else {
                v = currClass.getGlobalVars().get(ctx.ID().toString());
            }
            if (symbolTable.findClass(v.getType()) != null) {
                typeChecker.push(new Type(symbolTable.findClass(v.getType())));
            }
            else {
                typeChecker.push(new Type(v.getType()));
            }
        }

        if (ctx.THIS() != null) {
           typeChecker.push(new Type(currClass));
        }

        if (ctx.RSQUARE() != null && ctx.LSQUARE() != null && ctx.NEW() != null) {
            String s1 = typeChecker.pop().getRecordType();
            if (s1.equals("int")) {
                typeChecker.push(new Type("int[]"));
            }
            else {
                System.err.println("ERROR: Array sizes must be an integer");
            }
        }

        if (ctx.NEW() != null && ctx.ID() != null) {
            String objName = ctx.ID().toString();
            ClassRecord obj = symbolTable.findClass(objName);
            if (obj == null) {
                typeChecker.push(new Type("class"));
                System.err.println("ERROR: Object has no class");
            }
            else {
                typeChecker.push(new Type(obj));
            }
        }

        if (ctx.NOT() != null) {
            String s1 = typeChecker.pop().getRecordType();
            if (s1.equals("boolean")) {
                typeChecker.push(new Type("boolean"));
            }
            else {
                System.err.println("ERROR: Not arguments can only be of type boolean");
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


