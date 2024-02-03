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

`java pl1transpiler/Main "${pwd}/pl1transpiler/input/code.pli"`

to transpile your code. Your Java Code is written in /pl1transpiler/output/Main.java. Also the console log shows the translated code with the symboltable and the parsetree.
## What is possible?
Currently only declarations (Copybooks) can be translated.
###Known Issues
It's not possible to transform nested declarations that have a higher depth than level 2. Also if you want to transpile multiple nested declaration be aware that you need to maintain the result. In the current version the output Code got some logical errors you need to check.