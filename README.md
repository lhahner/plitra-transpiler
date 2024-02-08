# pl1-code-transpiler v0.1: Read me
## Installation
### Requirements
At least Java 8 and JavaCC is required. Please make sure to always compile the project in Java 8. If you don't the program will not work.
The Transpiler runs fine in Windows 11, MacOS 13.6.1 and Linux Ubuntu 22.

## How to transpile PL/I Code to Java?
### CLI
First check the pl1transpiler/input folder. There should be your PL/I code. I do not recommend to transpile code from outside of the project structure.
Simply navigate into the src folder of the project structure.
Run:

`javac --release 8 pl1transpiler/Main.java`

to compile the project.
Go back to the root directory of the projcet with:

`cd ..`

Afterwards run:

`java -classpath src pl1transpiler.Main "./src/pl1transpiler/input/code.pli"`

to transpile your code. Your Java Code is written in /pl1transpiler/output/Main.java. Also the console log shows the translated code with the symboltable and the parsetree.

### Eclipse
If you want to run the Application in Eclipse please follow these steps.
1. Open Project: File > Open Projects from File Systems > Choose the Repo Directory
2. Go to Run > Run Configurations > Arguments > paste in; Program arguments: "./src/pl1transpiler/input/code.pli"
3. Run the program

This is due the fact that eclipse needs to open the project from one folder above the "src" folder.

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
| Comments           | yes, just small  |

### Regarding Comments
Please be aware that all the comments provided will be excluded in the transpiled java code.
Also currently large lines of comments will cause an error an make the program fail.
If you have large amount of comments in your copybook I recommend to remove them. Anyways,
small amounts of comments like `/*Example Text*/` Will be removed.

### Known Issues
- Please be aware that that your folder is not inside a cloud like OneDrive. This can lead to problems.
- If you recieve an error while running the program in Eclipse you need to specify run configurations. Go to "Run > Run Configurations > Arguments > paste './src/pl1transpiler/input/code.pli'". Also you need to maintain the throughtputFile directory in class Main.
- It's not possible to transform nested declarations that have a higher depth than level 2. Also if you want to transpile multiple nested declaration be aware that you need to maintain the result. In the current version the output Code got some logical errors you need to check.
- Large comments cause an error.
