javac -cp "./src/:../antlr-4.7.2-complete.jar" -d myOut src/minijava/MiniJavaMain.java
java -cp "./myOut:../antlr-4.7.2-complete.jar" minijava.MiniJavaMain ../tests/TreeVisitor.java 
