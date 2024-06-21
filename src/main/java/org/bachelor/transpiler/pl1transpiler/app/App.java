/*
 * Copyright (c) 2023-2024, Lennart Hahner
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
import org.bachelor.transpiler.pl1transpiler.mapper.MainMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.MapperStrategy.Mapper;
import org.bachelor.transpiler.pl1transpiler.mapper.MapperStrategy.ProgramMapper;
import org.bachelor.transpiler.pl1transpiler.parser.*;
import org.bachelor.transpiler.pl1transpiler.scanner.*;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

/**
 * The Class App is used to define all objects for the classes in each module.
 * Here the Administrator can update, delete and insert new or old modules of the project.
 * If a module is not called here, it might be deprecated or only used by specified modules.
 * More Information about the Structure of Modules here:
 * {@link https://github.com/lhahner/model-pl1-code-transpiler}
 * @author Lennart Hahner
 */
public class App {
	
	/** Contains the Path to the Configuration File. */
	static final File CONFIG = new File("./src/main/java/res/config/config.propreties");
	
	/** Loads the InputReader Class, which is the entry for the scanner module. */
	static InputReader inputReader;
	
	/**  Loads the Pl1Parser Class, which is the entry for the parser module. */
	static Pl1Parser pl1Parser;
	
	/** Loads the SymbolTable Class, which is the entry for the Symbol-table module. */
	static SymbolTable symboltable;
	
	/** Loads the TypeChecker Class, which is the entry for the Checker module. */
	static TypeChecker typechecker;
	
	static ProgramMapper mapper;
	
	/**
	 * The main method.
	 *
	 * @param args								Used whenever the Program is compiled and build by the Command-line, null if not processed
	 * 
	 * @throws IncorrectInputFileException		This Exception is thrown whenever the File given as Input is not a PL/I file.
	 * 									  		It is checked, by checking the appendix of ".pli". No other File-type is allowed.
	 * 
	 * @throws IOException						Signals that an I/O exception has occurred. Is thrown whenever there is no File found. For
	 * 					  						the path specified in the configuration-file.
	 * 
	 * @throws ParseException					Signals whenever there is an syntactical error of the PL/I-Input Code. This Class is
	 * 						 					part of the boilerplate code of the auto generated parser.
	 */
	public static void main(String[] args) throws IncorrectInputFileException, IOException, ParseException {
		symboltable = new SymbolTable();
		inputReader = new InputReader();
		String inputFile = inputReader.getInputFilePath(CONFIG);
		typechecker = new TypeChecker();
		Mapper mapper;
		
		if (inputFile.toString().contains(".pli")) {

			try {
				pl1Parser = new Pl1Parser(inputReader.getInputFile(inputFile));
				SimpleNode root = pl1Parser.program();
				symboltable.printAll();
				root.dump(" ");
//				try {
//					typechecker.checkIdType(root);
//				} 
//				catch(IncompatibleTypeException IRE) {
//					IRE.printStackTrace();
//				}
			    mapper = new Mapper(root);
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
