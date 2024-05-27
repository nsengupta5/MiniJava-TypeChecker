## MiniJava Type Checker

### Overview

This project implements a type checker for MiniJava, a subset of the Java programming language. The type checking process involves building a symbol table, scope checking, and type checking statements and expressions. The implementation successfully passes 33 custom and provided MiniJava tests (16 for scope checking, 17 for type checking), demonstrating robust and reliable development practices.

### Compilation and Running Instructions

1. **Navigate to the MiniJava - ANTLR/ folder**
   ```sh
   cd MiniJava-ANTLR/
   ```

2. **Compile the program**
   ```sh
   javac -cp "./src/:../antlr-4.7.2-complete.jar" -d myOut src/minijava/MiniJavaMain.java
   ```

3. **Run the program**
   ```sh
   java -cp "./myOut:../antlr-4.7.2-complete.jar" minijava.MiniJavaMain <FILENAME>
   ```

   - `<FILENAME>` should be a file from the `../tests/` directory.
     - Custom tests are located in `../tests/myTests/`
     - Given tests are located in `../tests/`

### Design & Implementation
The process of type checking has four main steps:

- **Step 1: Symbol Table Construction**

  - Java’s object-oriented features were employed to build up classes for representing variables, methods and classes.

  - Custom data structures and collections were used to manage scope and resolve symbols.

- **Step 2: Inheriting Checks**

  - Checks on inheritance were implemented to ensure that method overriding works correctly across class hierarchies.

  - Cyclic inheritance was dealt with through Java’s exception handling mechanisms for errors.

- **Step 3: Statement and Expression Scope Checking**

  - The collection framework in java was applied to monitor the range as well as validate any variables and methods within it optimally.

  - Classes and routines were well organized to facilitate accurate symbol resolution.

- **Step 4: Statement and Expression Type Checking**

  - An expression is evaluated or checked using a stack-based approach
