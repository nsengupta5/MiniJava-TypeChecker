// Generated from MiniJavaGrammar.g by ANTLR 4.7.2
package minijava;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniJavaGrammarParser}.
 */
public interface MiniJavaGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MiniJavaGrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MiniJavaGrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#mainclass}.
	 * @param ctx the parse tree
	 */
	void enterMainclass(MiniJavaGrammarParser.MainclassContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#mainclass}.
	 * @param ctx the parse tree
	 */
	void exitMainclass(MiniJavaGrammarParser.MainclassContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#classdecl}.
	 * @param ctx the parse tree
	 */
	void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#classdecl}.
	 * @param ctx the parse tree
	 */
	void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void enterVardecl(MiniJavaGrammarParser.VardeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void exitVardecl(MiniJavaGrammarParser.VardeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#methoddecl}.
	 * @param ctx the parse tree
	 */
	void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#methoddecl}.
	 * @param ctx the parse tree
	 */
	void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#formallist}.
	 * @param ctx the parse tree
	 */
	void enterFormallist(MiniJavaGrammarParser.FormallistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#formallist}.
	 * @param ctx the parse tree
	 */
	void exitFormallist(MiniJavaGrammarParser.FormallistContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#formalrest}.
	 * @param ctx the parse tree
	 */
	void enterFormalrest(MiniJavaGrammarParser.FormalrestContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#formalrest}.
	 * @param ctx the parse tree
	 */
	void exitFormalrest(MiniJavaGrammarParser.FormalrestContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MiniJavaGrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MiniJavaGrammarParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BlockStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(MiniJavaGrammarParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BlockStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(MiniJavaGrammarParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfElseStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfElseStmt(MiniJavaGrammarParser.IfElseStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfElseStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfElseStmt(MiniJavaGrammarParser.IfElseStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MiniJavaGrammarParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MiniJavaGrammarParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStmt(MiniJavaGrammarParser.PrintStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStmt(MiniJavaGrammarParser.PrintStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(MiniJavaGrammarParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(MiniJavaGrammarParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrAssignStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterArrAssignStmt(MiniJavaGrammarParser.ArrAssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrAssignStmt}
	 * labeled alternative in {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitArrAssignStmt(MiniJavaGrammarParser.ArrAssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GroupExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGroupExpr(MiniJavaGrammarParser.GroupExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GroupExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGroupExpr(MiniJavaGrammarParser.GroupExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstBoolExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConstBoolExpr(MiniJavaGrammarParser.ConstBoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstBoolExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConstBoolExpr(MiniJavaGrammarParser.ConstBoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewObjExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewObjExpr(MiniJavaGrammarParser.NewObjExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewObjExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewObjExpr(MiniJavaGrammarParser.NewObjExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrIndexExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrIndexExpr(MiniJavaGrammarParser.ArrIndexExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrIndexExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrIndexExpr(MiniJavaGrammarParser.ArrIndexExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewArrExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewArrExpr(MiniJavaGrammarParser.NewArrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewArrExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewArrExpr(MiniJavaGrammarParser.NewArrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVarExpr(MiniJavaGrammarParser.VarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVarExpr(MiniJavaGrammarParser.VarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrLengthExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrLengthExpr(MiniJavaGrammarParser.ArrLengthExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrLengthExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrLengthExpr(MiniJavaGrammarParser.ArrLengthExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(MiniJavaGrammarParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(MiniJavaGrammarParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OpExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOpExpr(MiniJavaGrammarParser.OpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OpExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOpExpr(MiniJavaGrammarParser.OpExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstIntExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConstIntExpr(MiniJavaGrammarParser.ConstIntExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstIntExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConstIntExpr(MiniJavaGrammarParser.ConstIntExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ThisExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterThisExpr(MiniJavaGrammarParser.ThisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ThisExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitThisExpr(MiniJavaGrammarParser.ThisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MethodCallExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMethodCallExpr(MiniJavaGrammarParser.MethodCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MethodCallExpr}
	 * labeled alternative in {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMethodCallExpr(MiniJavaGrammarParser.MethodCallExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#op}.
	 * @param ctx the parse tree
	 */
	void enterOp(MiniJavaGrammarParser.OpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#op}.
	 * @param ctx the parse tree
	 */
	void exitOp(MiniJavaGrammarParser.OpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#exprlist}.
	 * @param ctx the parse tree
	 */
	void enterExprlist(MiniJavaGrammarParser.ExprlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#exprlist}.
	 * @param ctx the parse tree
	 */
	void exitExprlist(MiniJavaGrammarParser.ExprlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#exprrest}.
	 * @param ctx the parse tree
	 */
	void enterExprrest(MiniJavaGrammarParser.ExprrestContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#exprrest}.
	 * @param ctx the parse tree
	 */
	void exitExprrest(MiniJavaGrammarParser.ExprrestContext ctx);
}