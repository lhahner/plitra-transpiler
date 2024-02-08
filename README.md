# pl1-code-transpiler v0.1: Read me
## Installation
### Requirements
At least Java 8 and JavaCC is required. Please make sure to always compile the project in Java 8. If you don't the program will not work.
## How to transpile PL/I Code to Java?
First check the pl1transpiler/input folder. There should be your PL/I code. I do not recommend to transpile code from outside of the project structure.
Simply navigate into the src folder of the project structure.
Run:

`javac --release 8 pl1transpiler/Main.java`

to compile the project.
Afterwards run:

`java pl1transpiler/Main "./pl1transpiler/input/code.pli"`

to transpile your code. Your Java Code is written in /pl1transpiler/output/Main.java. Also the console log shows the translated code with the symboltable and the parsetree.
## What is possible?
Currently only declarations (Copybooks) can be translated.

### Reference Table 

| PL/I Expression    | Implemented      |
| ------------------ | ---------------- |
| DECLARE/DCL        | yes, complete    |
| Levels             | yes, not nested  |
| DECIMAL            | yes, complete    |
| FIXED DECIMAL      | yes, complete    |
| FLOAT DECIMAL      | yes, complete    |
| COMPLEX DECIMAL    | yes, complete    |
| REAL DECIMAL       | yes, complete    |
| CHAR               | yes, complete    |
| WIDECHAR           | yes, complete    |
| GRAPHIC            | yes, complete    |
| BIT                | yes, complete    |
| BINARY             | yes, complete    |
| PICTURE/PIC        | yes, complete    |

### Regarding Comments
Please be aware that all the comments provided will be excluded in the transpiled java code.
Also currently large lines of comments will cause an error an make the program fail.
If you have large amount of comments in your copybook I recommend to remove them. Anyways,
small amounts of comments like `/*Example Text*/` Will be removed.

### Known Issues
- Please be aware that you that your folder is not inside a cloud like OneDrive. This can lead to problems.
- If you recieve an error while running the program in Eclipse you need to specify run configurations. Go to "Run > Run Configurations > Arguments > paste './src/pl1transpiler/input/code.pli'". Also you need to maintain the throughtputFile directory in class Main.
- If you recieve an error while running it in a specific folder. Maybe try another folder, sometimes the pwd statement is not correct.
- It's not possible to transform nested declarations that have a higher depth than level 2. Also if you want to transpile multiple nested declaration be aware that you need to maintain the result. In the current version the output Code got some logical errors you need to check.
