package minijava;

import minijava.symboltable.*;

import java.util.List;
import java.util.Stack;

public class TypeChecker extends MiniJavaGrammarBaseListener {
    private SymbolTable symbolTable;
    private ClassRecord currClass;
    private MethodRecord currMethod;
    private Stack<Type> typeChecker;

    public TypeChecker(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        typeChecker = new Stack<>();
    }

    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        currClass = symbolTable.findClass(ctx.ID(0).toString());
    }

    @Override
    public void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        currMethod = currClass.getMethods().get(ctx.ID().toString());
    }

    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
        Type s1 = typeChecker.pop();
        String t;
        if (s1.getObject() != null) {
            ClassRecord c = s1.getObject();
            t = c.getId();
        }
        else {
            t = s1.getRecordType();
        }
        if (!currMethod.getType().equals(t)) {
            System.err.println("ERROR: Invalid return type");
        }
    }

    @Override
    public void exitIfElseStmt(MiniJavaGrammarParser.IfElseStmtContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("boolean")) {
            System.err.println("ERROR: Conditions of if statements must be of type boolean");
        }
    }

    @Override
    public void exitWhileStmt(MiniJavaGrammarParser.WhileStmtContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("boolean")) {
            System.err.println("ERROR: Conditions of while statements must be of type boolean");
        }
    }

    @Override
    public void exitPrintStmt(MiniJavaGrammarParser.PrintStmtContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("int")) {
            System.err.println("ERROR: Arguments of Println must be of type integer");
        }
    }

    @Override
    public void exitAssignStmt(MiniJavaGrammarParser.AssignStmtContext ctx) {
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

    @Override
    public void exitArrAssignStmt(MiniJavaGrammarParser.ArrAssignStmtContext ctx) {
        String arr;
        if (currMethod.getParameters().get(ctx.ID().toString()) != null) {
           arr = currMethod.getParameters().get(ctx.ID().toString()).getType();
        }
        else if (currMethod.getLocalVars().get(ctx.ID().toString()) != null) {
            arr = currMethod.getLocalVars().get(ctx.ID().toString()).getType();
        }
        else {
            arr = currClass.getGlobalVars().get(ctx.ID().toString()).getType();
        }
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

    @Override
    public void enterMethodCallExpr(MiniJavaGrammarParser.MethodCallExprContext ctx) {
        typeChecker.push(null);
    }

    @Override
    public void exitMethodCallExpr(MiniJavaGrammarParser.MethodCallExprContext ctx) {
        Stack<Type> args = new Stack<>();
        while (typeChecker.peek() != null) {
            args.push(typeChecker.pop());
        }

        typeChecker.pop();
        Type methodType = args.pop();
        if (!methodType.getRecordType().equals("class")) {
            System.err.println("ERROR: Only objects can call methods");
            // URGENT: NEED TO LOOK AT THIS
            typeChecker.push(new Type("class"));
            return;
        }

        ClassRecord c = methodType.getObject();
        if (c == null) {
            System.err.println("ERROR: Class does not exist");
            System.exit(-1);
        }
        else {
            MethodRecord m = c.getMethods().get(ctx.ID().toString());
            if (m == null) {
                System.err.println("ERROR: Given class does not contain this method");
                typeChecker.push(new Type("class"));
                return;
            }
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
    }

    @Override
    public void exitOpExpr(MiniJavaGrammarParser.OpExprContext ctx) {
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

    @Override
    public void exitArrIndexExpr(MiniJavaGrammarParser.ArrIndexExprContext ctx) {
        String index = typeChecker.pop().getRecordType();
        String arr = typeChecker.pop().getRecordType();
        if (!index.equals("int")) {
            System.err.println("ERROR: Index has to be an integer");
        }
        if (!arr.equals("int[]")) {
            System.err.println("ERROR: Array lookup has to be an integer");
        }
        typeChecker.push(new Type("int"));
    }

    @Override
    public void exitArrLengthExpr(MiniJavaGrammarParser.ArrLengthExprContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("int[]")) {
            System.err.println("ERROR: Length can only be applied to type int[]");
        }
        typeChecker.push(new Type("int"));
    }

    @Override
    public void exitVarExpr(MiniJavaGrammarParser.VarExprContext ctx) {
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

    @Override
    public void exitConstIntExpr(MiniJavaGrammarParser.ConstIntExprContext ctx) {
        typeChecker.push(new Type("int"));
    }

    @Override
    public void exitConstBoolExpr(MiniJavaGrammarParser.ConstBoolExprContext ctx) {
        typeChecker.push(new Type("boolean"));
    }

    @Override
    public  void exitThisExpr(MiniJavaGrammarParser.ThisExprContext ctx) {
        typeChecker.push(new Type(currClass));
    }

    @Override
    public void exitNewArrExpr(MiniJavaGrammarParser.NewArrExprContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("int")) {
            System.err.println("ERROR: Array sizes must be an integer");
        }
        typeChecker.push(new Type("int[]"));
    }

    @Override
    public void exitNewObjExpr(MiniJavaGrammarParser.NewObjExprContext ctx) {
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

    @Override
    public void exitNotExpr(MiniJavaGrammarParser.NotExprContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("boolean")) {
            System.err.println("ERROR: Not arguments can only be of type boolean");
        }
        typeChecker.push(new Type("boolean"));
    }
}


