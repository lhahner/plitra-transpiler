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
import org.bachelor.transpiler.pl1transpiler.mapper.MainMapper;
import org.bachelor.transpiler.pl1transpiler.parser.*;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

public class App {
	
	static final File CONFIG = new File("./src/main/java/res/config/input-config");
	static Pl1Parser pl1Parser;
	
	/**
	 * @param args[0] holds the path to the input file.
	 * @throws IncorrectInputFileException
	 */
	public static void main(String[] args) throws IncorrectInputFileException, IOException, ParseException {
		SymbolTable st = new SymbolTable();
		InputReader inputReader = new InputReader();
		String inputFile = inputReader.getInputFilePath(CONFIG);
		
		if (inputFile.toString().contains(".pli")) {

			try {
				pl1Parser = new Pl1Parser(inputReader.getInputFile(inputFile));
				SimpleNode root = pl1Parser.program();
				pl1Parser.installIds();
				st.printAll();
				root.dump(" ");
				
				// load Java Parser and give Pl1parser
				//MainMapper jP = new MainMapper(pl1Parser);
				// create expression with Parsetree
				//jP.createExpression(root);
				//System.out.println(jP.concatExpression());
				//final File javaFile = new File("./target/transpiled-sources/" + inputReader.getProgramname() + ".java");
				//FileWriter writeFile = new FileWriter(javaFile);
				//writeFile.write(jP.concatExpression());
				//writeFile.close();

			} catch (IOException e) {
				throw new IOException(e);
			}
		} else {
			throw new IncorrectInputFileException();
		}

	}
}
