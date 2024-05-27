## Compilation and Running

- Navigate to the MiniJava - ANTLR/ folder
- Compile using `javac -cp "./src/:../antlr-4.7.2-complete.jar" -d myOut src/minijava/MiniJavaMain.java`
- Run using `java -cp "./myOut:../antlr-4.7.2-complete.jar" minijava.MiniJavaMain <FILENAME> `
    - \<FILENAME\> can be any files from the test folder, that are located in `../tests/`
        - Custom tests are located at `../tests/myTests/`
        - Given tests are located at `../tests/`
