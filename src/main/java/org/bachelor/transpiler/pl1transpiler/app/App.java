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

import org.bachelor.transpiler.pl1transpiler.checker.Checker;
import org.bachelor.transpiler.pl1transpiler.checker.HierachieChecker;
import org.bachelor.transpiler.pl1transpiler.checker.ReferenceChecker;
import org.bachelor.transpiler.pl1transpiler.checker.TypeChecker;
import org.bachelor.transpiler.pl1transpiler.errorhandling.IdentifierReferenceException;
import org.bachelor.transpiler.pl1transpiler.errorhandling.IncompatibleTypeException;
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
		TypeChecker typechecker = new TypeChecker();
		
		if (inputFile.toString().contains(".pli")) {

			try {
				pl1Parser = new Pl1Parser(inputReader.getInputFile(inputFile));
				SimpleNode root = pl1Parser.program();
				st.printAll();
				root.dump(" ");
				
				
				try {
					typechecker.checkIdType(root);
				} 
				catch(IncompatibleTypeException IRE) {
					IRE.printStackTrace();
				}
				
				// load Java Parser and give Pl1parser
				//MainMapper jP = new MainMapper(pl1Parser);
				// create expression with Parsetree
				//jP.createExpression(root);
				//System.out.println(jP.concatExpression());
				//final File javaFile = new File("./transpiled-sources/" + inputReader.getProgramname() + ".java");
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
