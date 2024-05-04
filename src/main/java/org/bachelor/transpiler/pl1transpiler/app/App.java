package org.bachelor.transpiler.pl1transpiler.app;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import org.bachelor.transpiler.pl1transpiler.errorhandling.IncorrectInputFileException;
import org.bachelor.transpiler.pl1transpiler.lexer.*;
import org.bachelor.transpiler.pl1transpiler.parser.*;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.bachelor.transpiler.pl1transpiler.synthesizer.JavaGenerator;

public class App {
	static String inputFile;
	static Pl1Parser pl1Parser;

	/**
	 * @param args[0] holds the path to the input file.
	 * @throws IncorrectInputFileException
	 */
	public static void main(String[] args) throws IncorrectInputFileException {
		SymbolTable st = new SymbolTable();
		InputFormatter inputFormatter = new InputFormatter();
		inputFile = args[0];
		if (inputFile.toString().contains(".pli")) {
			try {
				pl1Parser = new Pl1Parser(inputFormatter.formatInputFile(inputFile));
				SimpleNode root = pl1Parser.program();
				st.printAll();
				root.dump(" ");
				// load Java Parser and give Pl1parser
				JavaGenerator jP = new JavaGenerator(pl1Parser);
				// create expression with Parsetree
				jP.createExpression(root);
				System.out.println(jP.concatExpression());
				final File javaFile = new File("./src/main/java/resources/java/Main.java");
				FileWriter writeFile = new FileWriter(javaFile);
				writeFile.write(jP.concatExpression());
				writeFile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new IncorrectInputFileException();
		}

	}
}
