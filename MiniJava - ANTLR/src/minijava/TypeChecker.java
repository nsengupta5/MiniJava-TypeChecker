package minijava;

import minijava.symboltable.*;

import java.util.List;
import java.util.Stack;

public class TypeChecker extends MiniJavaGrammarBaseListener {
    private SymbolTable symbolTable;
    private ClassRecord currClass;
    private MethodRecord currMethod;
    private Stack<Type> typeChecker;

    /**
     * Constructor for the TypeChecker
     * @param symbolTable Symbol table from previous pass
     */
    public TypeChecker(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        typeChecker = new Stack<>();
    }

    /**
     * Sets the current class
     * @param ctx the parse tree
     */
    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
        currClass = symbolTable.findClass(ctx.ID(0).toString());
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
     * Checks if the return type matches the method's type
     * @param ctx the parse tree
     */
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

    /**
     * Checks if the "if" statement correctly contains a boolean
     * @param ctx the parse tree
     */
    @Override
    public void exitIfElseStmt(MiniJavaGrammarParser.IfElseStmtContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("boolean")) {
            System.err.println("ERROR: Conditions of if statements must be of type boolean");
        }
    }

    /**
     * Checks if the "while" statement correctly contains a boolean
     * @param ctx the parse tree
     */
    @Override
    public void exitWhileStmt(MiniJavaGrammarParser.WhileStmtContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("boolean")) {
            System.err.println("ERROR: Conditions of while statements must be of type boolean");
        }
    }

    /**
     * Checks if the "Println" statement correctly contains an int
     * @param ctx the parse tree
     */
    @Override
    public void exitPrintStmt(MiniJavaGrammarParser.PrintStmtContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("int")) {
            System.err.println("ERROR: Arguments of Println must be of type integer");
        }
    }

    /**
     * Checks for the correct matching types when assigning variables
     * @param ctx the parse tree
     */
    @Override
    public void exitAssignStmt(MiniJavaGrammarParser.AssignStmtContext ctx) {
        VarRecord v;
        // Finds the variable in the scope
        if (currMethod.getLocalVars().get(ctx.ID().toString()) != null) {
            v = currMethod.getLocalVars().get(ctx.ID().toString());
        }
        else if (currMethod.getParameters().get(ctx.ID().toString()) != null) {
            v = currMethod.getParameters().get(ctx.ID().toString());
        }
        else {
            v = currClass.getGlobalVars().get(ctx.ID().toString());
        }
        String lhs;
        // Check for objects
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

    /**
     * Checks if an int[] array is correctly assigned to the right type
     * @param ctx the parse tree
     */
    @Override
    public void exitArrAssignStmt(MiniJavaGrammarParser.ArrAssignStmtContext ctx) {
        String arr;
        // Gets the array type
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

        // Checks if the assigned variable is an int[]
        if (!arr.equals("int[]")) {
            System.err.println("ERROR: Variable must be of type int[]");
        }

        // Checks if the type being assigned to int[] index is an integer
        if (!lhs.equals("int")) {
            System.err.println("ERROR: Int array element must be assigned to an integer");
        }

        // Checks that the int[] index is an integer
        if (!index.equals("int")) {
            System.err.println("ERROR: Index of arrays must be an integer");
        }
    }

    /**
     * Adds a marker to denote the start of an expression call
     * @param ctx the parse tree
     */
    @Override
    public void enterMethodCallExpr(MiniJavaGrammarParser.MethodCallExprContext ctx) {
        typeChecker.push(null);
    }

    /**
     * Checks for correct method call type
     * @param ctx the parse tree
     */
    @Override
    public void exitMethodCallExpr(MiniJavaGrammarParser.MethodCallExprContext ctx) {
        Stack<Type> args = new Stack<>();
        // Get all arguments of a method call
        while (typeChecker.peek() != null) {
            args.push(typeChecker.pop());
        }

        typeChecker.pop();
        Type methodType = args.pop();
        // Checks that the variable calling the method is an object
        if (!methodType.getRecordType().equals("class")) {
            System.err.println("ERROR: Only objects can call methods");
            // URGENT: NEED TO LOOK AT THIS
            typeChecker.push(new Type("class"));
            return;
        }

        ClassRecord c = methodType.getObject();
        // Checks if class exists
        if (c == null) {
            System.err.println("ERROR: Class does not exist");
            System.exit(-1);
        }
        else {
            // Checks if the object contains the method
            MethodRecord m = c.getMethods().get(ctx.ID().toString());
            if (m == null) {
                System.err.println("ERROR: Given class does not contain this method");
                typeChecker.push(new Type("class"));
                return;
            }
            List<String> params = m.getParamTypes();
            // Checks if the method call has the correct number of arguements
            if (params.size() != args.size()) {
                System.err.println("ERROR: Method call has invalid number of arguments");
                typeChecker.push(new Type("class"));
                return;
            }

            /*Checks that the types in the method call match the order in which is declared
             in the method */
            for (String param : params) {
                Type p = args.pop();
                String t = p.getRecordType();
                // CHECK CLASS TYPES E.G ACCEPT METHOD IN TREEVISITOR
                if (p.getObject() != null) {
                    if (!p.getObject().getId().equals(param)) {
                        System.err.println("ERROR: Argument type not compatible");
                    }
                }
                else {
                    if (!t.equals(param)) {
                        System.err.println("ERROR: Argument type not compatible");
                    }
                }
            }

            // Pushes the corresponding type to the typechecker stack
            if (symbolTable.findClass(m.getType()) != null) {
                typeChecker.push(new Type(symbolTable.findClass(m.getType())));
            }
            else {
                typeChecker.push(new Type(m.getType()));
            }
        }
    }

    /**
     * Checks for correct types during operations
     * @param ctx the parse tree
     */
    @Override
    public void exitOpExpr(MiniJavaGrammarParser.OpExprContext ctx) {
        // Gets 2 most recent types
        String s1 = typeChecker.pop().getRecordType();
        String s2 = typeChecker.pop().getRecordType();
        if (ctx.op().AND() == null) {
            // Checks if integer operations are performed on integers
            if (!(s1.equals("int") && s2.equals("int"))) {
                System.err.println("ERROR: Both not integers");
            }
        }
        else {
            // Checks if boolean operations are performed on booleans
            if (!(s1.equals("boolean") && s2.equals("boolean"))) {
                System.err.println("ERROR: Both not booleans");
            }
        }

        // Pushes boolean onto stack for expressions evaluating to booleans
        if (ctx.op().LT() != null || ctx.op().AND() != null) {
            typeChecker.push(new Type("boolean"));
        }
        // Pushes int onto stack for expressions evaluating to int
        else {
            typeChecker.push(new Type("int"));
        }
    }

    /**
     * Cehcks for correct int[] expression type
     * @param ctx the parse tree
     */
    @Override
    public void exitArrIndexExpr(MiniJavaGrammarParser.ArrIndexExprContext ctx) {
        String index = typeChecker.pop().getRecordType();
        String arr = typeChecker.pop().getRecordType();
        // Checks if index is an integer
        if (!index.equals("int")) {
            System.err.println("ERROR: Index has to be an integer");
        }
        // Checks that lookup is being applied to int[]
        if (!arr.equals("int[]")) {
            System.err.println("ERROR: Array lookup has to be an integer");
        }
        // Pushes into to typechcker stack
        typeChecker.push(new Type("int"));
    }

    /**
     * Checks for correct length application to int[]
     * @param ctx the parse tree
     */
    @Override
    public void exitArrLengthExpr(MiniJavaGrammarParser.ArrLengthExprContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        // Checks that length method only applied to int[]
        if (!s1.equals("int[]")) {
            System.err.println("ERROR: Length can only be applied to type int[]");
        }
        typeChecker.push(new Type("int"));
    }

    /**
     * Pushes a variable type to the typechecker class
     * @param ctx the parse tree
     */
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

    /**
     * Pushes an integer to typechecker stack
     * @param ctx the parse tree
     */
    @Override
    public void exitConstIntExpr(MiniJavaGrammarParser.ConstIntExprContext ctx) {
        typeChecker.push(new Type("int"));
    }

    /**
     * Pushes a boolean to typechecker stack
     * @param ctx the parse tree
     */
    @Override
    public void exitConstBoolExpr(MiniJavaGrammarParser.ConstBoolExprContext ctx) {
        typeChecker.push(new Type("boolean"));
    }

    /**
     * Pushes current class to typechecker stack
     * @param ctx the parse tree
     */
    @Override
    public  void exitThisExpr(MiniJavaGrammarParser.ThisExprContext ctx) {
        typeChecker.push(new Type(currClass));
    }

    /**
     * Checks for correct int[] declaration
     * @param ctx the parse tree
     */
    @Override
    public void exitNewArrExpr(MiniJavaGrammarParser.NewArrExprContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        // Checks size given is an integer
        if (!s1.equals("int")) {
            System.err.println("ERROR: Array sizes must be an integer");
        }
        typeChecker.push(new Type("int[]"));
    }

    /**
     * Checks that the new object declaration has a corresponding class declaration
     * @param ctx the parse tree
     */
    @Override
    public void exitNewObjExpr(MiniJavaGrammarParser.NewObjExprContext ctx) {
        String objName = ctx.ID().toString();
        ClassRecord obj = symbolTable.findClass(objName);
        if (obj == null) {
            // Push even if class doesn't exist
            typeChecker.push(new Type("class"));
            System.err.println("ERROR: Object has no class");
        }
        else {
            typeChecker.push(new Type(obj));
        }
    }

    /**
     * Checks if the not expression contains a boolean
     * @param ctx the parse tree
     */
    @Override
    public void exitNotExpr(MiniJavaGrammarParser.NotExprContext ctx) {
        String s1 = typeChecker.pop().getRecordType();
        if (!s1.equals("boolean")) {
            System.err.println("ERROR: Not arguments can only be of type boolean");
        }
        // Assumes boolean even if incorrect
        typeChecker.push(new Type("boolean"));
    }
}