package minijava;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class MiniJavaMain {

    public static void main(String[] args) throws Exception {

        String inputFile = null;

        if (args.length > 0 )
            inputFile = args[0];

        InputStream is = System.in;

        if (inputFile != null ) is = new FileInputStream(inputFile);

        ANTLRInputStream input = new ANTLRInputStream(is);

        MiniJavaGrammarLexer lexer = new MiniJavaGrammarLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniJavaGrammarParser parser = new MiniJavaGrammarParser(tokens);

        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();

        SymbolTableBuilder sbBuilder = new SymbolTableBuilder(parser);

        walker.walk(sbBuilder, tree);

        InheritanceListener inheritance = new InheritanceListener(sbBuilder.getSymbolTable());

        walker.walk(inheritance, tree);

        ScopeCheckingStatements verifier = new ScopeCheckingStatements(inheritance.getSymbolTable());

        walker.walk(verifier, tree);

//        TypeChecker typecheck = new TypeChecker(verifier.getSymbolTable());
//
//        walker.walk(typecheck, tree);

    }


}
