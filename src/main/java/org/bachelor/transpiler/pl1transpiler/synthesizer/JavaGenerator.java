package org.bachelor.transpiler.pl1transpiler.synthesizer;
//Api imports
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JRootPane;

import org.bachelor.transpiler.pl1transpiler.parser.*;
import org.bachelor.transpiler.pl1transpiler.symboltable.PictureMapper;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class JavaGenerator {

	static int iterationCounter = 0;
	static int lengthCounter = 0;
	SymbolTable symboltable = new SymbolTable();
	static ArrayList<String> java_expression = new ArrayList<String>();
	Pl1Parser pl1Parser;
	String Major;
	Template javaWords;
	
	public JavaGenerator() {
		
	}
	
	public JavaGenerator(Pl1Parser pp) {
		this.pl1Parser = pp;
	}

	/**
	 * @return the translated Java Programm as a String
	 */
	public String concatExpression() {
		try {

			if (java_expression.size() != 0) {
				String listString = java_expression.stream().map(Object::toString).collect(Collectors.joining(""));
				listString = javaWords.PACKAGE.getValue() + "\n" + javaWords.IMPORTS.getValue() + "\n"
						+ javaWords.MAIN_CLASS.getValue() + "\n" + javaWords.MAIN_METHOD.getValue() + listString
						+ "} \n" + javaWords.DATATYPE_INTERFACE.getValue() + "\n" + javaWords.PICTURE_CLASS.getValue()
						+ "\n" + javaWords.PL1_CHAR_CLASS.getValue() + "\n" + javaWords.BINARY_CLASS.getValue() + "\n";
				return listString;

			}

			return "Java Expression is 0";

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return "Unhandled Error";
	}

	/**
	 * @param root the node at the beginning of the parse tree
	 */
	public void createExpression(Node root) {

		Node currentroot = root.jjtGetChild(0);
		String id = getId();
		int currentScope = getScope();

		for (int i = 0; i < root.jjtGetNumChildren(); i++) {
			if (root.jjtGetChild(i).toString() == "VAR") {
				for (int j = 0; j < root.jjtGetChild(i).jjtGetNumChildren(); j++) {
					createStructure(root.jjtGetChild(i).jjtGetChild(j));

				}
			}
		}
	}

	/**
	 * @param root the node at the beginning of a nested structure
	 */
	public void createStructure(Node root) {
		int currentScope = getScope();

		try {
			if (root.toString() == "TYPE") {
				addType(root);
			} else if (root.toString() == "MINOR" && currentScope == 1) {
				addClass();
				for (int i = 0; i < root.jjtGetNumChildren(); i++) {
					createStructure(root.jjtGetChild(i));
				}
			} else if (root.toString() == "MINOR" && currentScope > 1) {
				for (int i = 0; i < root.jjtGetNumChildren(); i++) {
					createStructure(root.jjtGetChild(i));
				}
			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println("Error in createStructure() " + e);
		}
	}

	/**
	 * @param root the node at the beginning of the type branch
	 */
	public void addType(Node root) {
		try {
			Node type = root.jjtGetChild(0);

			if (type.toString() == "String") {
				String idString;

				java_expression.add(javaWords.PRIVATE.getValue() + " PL1_CHAR ");
				idString = addIdentifier();
				java_expression.add(
						" = " + javaWords.NEW.getValue() + " PL1_CHAR(" + pl1Parser.getLength(lengthCounter) + ")");
				lengthCounter++;
				java_expression.add("; \n");

			} else if (type.toString() == "Arithmetic") {
				addArithmetic(type);
			} else if (type.toString() == "PictureExpression") {
				addPictureExpression();
			}
		} catch (Exception e) {
			System.out.println("Error in addType() : " + e);
		}
	}

	/**
	 * Called when the the Picture translation should be added to the Expression
	 * Array.
	 */
	public void addPictureExpression() {
		PictureMapper picmap = new PictureMapper();
		String picRegex = pl1Parser.getPictureAttirbute().replaceAll("\'", "");

		java_expression.add(javaWords.PICTURE.getValue() + " ");
		this.addIdentifier();
		java_expression.add(" = " + javaWords.NEW.getValue());
		java_expression.add(" " + javaWords.PICTURE.getValue() + " (\"" + picmap.getRegex(picRegex) + "\");\n");
	}

	/**
	 * @param className is the name of the Class that should be added to the
	 *                  Expresison Array.
	 */
	public void addObject(String className) {
		java_expression.add(className + " " + className + "_ = " + javaWords.NEW.getValue() + " " + className + "();");
	}

	/**
	 * @param root the node at the beginning of the Arithmetic branch.
	 */
	public void addArithmetic(Node root) {
		try {
			Node decimalType = root.jjtGetChild(root.jjtGetNumChildren() - 1).jjtGetChild(0);

			if (root.jjtGetChild(0).toString() == "Binary") {
				addBinary();
			} else {
				if (root.jjtGetNumChildren() > 2 && root.jjtGetChild(0).toString() != "Real") {
					addPrefixDecimal(root);
				} else {

					switch (decimalType.toString()) {
					case "Int":
						String idInt;
						if (java_expression.contains(Major)) {
							java_expression.add(javaWords.PRIVATE.getValue() + " " + javaWords.INT.getValue());
							idInt = addIdentifier();
							java_expression.add("; \n");
							addSetter(javaWords.INT.getValue(), idInt);
							addGetter(javaWords.INT.getValue(), idInt);
						} else {
							java_expression.add(javaWords.INT.getValue() + " ");
							idInt = addIdentifier();
							java_expression.add("; \n");
						}

						break;

					case "Double":
						String idDouble;
						if (java_expression.contains(Major)) {
							java_expression.add(javaWords.PRIVATE.getValue() + " " + javaWords.DOUBLE.getValue() + " ");
							idDouble = addIdentifier();
							java_expression.add("; \n");
							addSetter(javaWords.DOUBLE.getValue(), idDouble);
							addGetter(javaWords.DOUBLE.getValue(), idDouble);
						} else {
							java_expression.add(javaWords.DOUBLE.getValue() + " ");
							idDouble = addIdentifier();
							java_expression.add("; \n");
						}

						break;
					default:
						System.out.println("In default case");
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error in addArithmetic(): " + e);
		}
	}

	/**
	 * @param root the node at the beginning of the Decimal branch.
	 */
	public void addPrefixDecimal(Node root) {
		try {
			for (int i = 0; i < root.jjtGetNumChildren(); i++) {
				switch (root.jjtGetChild(i).toString()) {
				case "Complex":
					addComplex();
					break;
				case "Fixed":
					addBigDecimal();
					break;
				case "Float":
					addFloat();
					break;
				case "size":

					break;
				default:
					System.out.println("Error in addPrefixDecimal Methode");
				}
			}
		} catch (Exception e) {
			System.out.println("Error in addPrefixDecimal " + e);
		}
	}

	/**
	 * Adds the complex Structure to translate complex decimals to the
	 * java_expression.
	 */
	public void addComplex() {
		String id;
		String real = "real";
		String imag = "imag";

		java_expression.add(" " + javaWords.CLASS.getValue() + " ");
		addIdentifier();
		java_expression.add(" {\n");

		java_expression.add(javaWords.PRIVATE.getValue() + " " + javaWords.DOUBLE.getValue() + " " + real + ";\n");
		addGetter("double", real);
		addSetter("double", real);

		java_expression.add(javaWords.PRIVATE.getValue() + " " + javaWords.DOUBLE.getValue() + " " + imag + ";\n");
		addGetter(javaWords.DOUBLE.getValue(), imag);
		addSetter(javaWords.DOUBLE.getValue(), imag);
		java_expression.add("}\n");

	}

	/**
	 * Adds the Structure to translate binary to the java_expression.
	 */
	public void addBinary() {
		java_expression.add(javaWords.BINARY.getValue() + " ");
		this.addIdentifier();
		java_expression.add(" = " + javaWords.NEW.getValue());
		java_expression.add(" " + javaWords.BINARY.getValue() + " (" + pl1Parser.getLength(lengthCounter) + ");\n");
		lengthCounter++;
	}

	/**
	 * Adds the Structure to translate BigDecimal to the java_expression.
	 */
	public void addBigDecimal() {
		try {
			if (!(pl1Parser.getLength(lengthCounter).contains(","))) {
				String p = pl1Parser.getLength(lengthCounter);
				lengthCounter++;
				int precision = (int) Math.pow(10, Double.parseDouble(p) - 1);

				if (p.equals("1")) {
					precision = (int) Math.pow(10, 0);
				}

				java_expression.add(javaWords.BIGDECIMAL.getValue() + " ");
				this.addIdentifier();
				java_expression.add(" = " + javaWords.NEW.getValue() + " " + javaWords.BIGDECIMAL.getValue() + "(\""
						+ precision + "," + 0 + "\");");
			} else {
				// uses the parse tree value and safes the first digit and the second after the
				String p = pl1Parser.getLength(lengthCounter).substring(0,
						pl1Parser.getLength(lengthCounter).indexOf(","));
				String q = pl1Parser.getLength(lengthCounter).substring(pl1Parser.getLength(lengthCounter).indexOf(","),
						pl1Parser.getLength(lengthCounter).length()).replaceAll(",", "");
				int precision;
				int quantum;
				lengthCounter++;
				// Adds BigDecimal
				java_expression.add(javaWords.BIGDECIMAL.getValue());
				precision = (int) Math.pow(10, Double.parseDouble(p) - 1);
				quantum = (int) Math.pow(10, Double.parseDouble(q) - 1);

				if (p.equals("1")) {
					precision = (int) Math.pow(10, 0);
				}
				if (q.equals("1")) {
					quantum = (int) Math.pow(10, 0);
				}
				this.addIdentifier();
				java_expression.add(" = " + javaWords.NEW.getValue() + " " + javaWords.BIGDECIMAL.getValue() + "(\""
						+ precision + "," + quantum + "\");");
			}
		} catch (Exception e) {
			System.out.println("Error addBigDecimal: " + e);
		}
	}

	/**
	 * Adds the Structure to translate float decimal to the java_expression.
	 */
	public void addFloat() {
		String idFloat;
		if (java_expression.contains(Major)) {
			java_expression.add(javaWords.PRIVATE.getValue() + " " + javaWords.FLOAT.getValue());
			idFloat = addIdentifier();
			java_expression.add("; \n");
			addSetter(javaWords.FLOAT.getValue(), idFloat);
			addGetter(javaWords.FLOAT.getValue(), idFloat);
		} else {
			java_expression.add(javaWords.FLOAT.getValue());
			idFloat = addIdentifier();
			java_expression.add("; \n");
		}
	}

	/**
	 * Adds the Structure to translate float decimal to the java_expression.
	 * 
	 * @return the identifier of the class.
	 */
	public String addClass() {
		try {
			addObject(symboltable.getAllIdentifier().get(iterationCounter)[0]);
			Major = getId();
			java_expression.add("} \n" + javaWords.CLASS.getValue() + " ");
			String id = addIdentifier();
			java_expression.add(" { \n ");
			return id;
		} catch (Exception e) {
			System.out.println("Error in addClass(): " + e);
			return "";
		}
	}

	/**
	 * @param type of the Methode that should be added to the java_expression.
	 * @param id   name of the Methode that should be added to the java_expression.
	 */
	public void addMethod(String type, String id) {
		java_expression.add(javaWords.PUBLIC.getValue() + " " + type + " " + id + "() { \n");
		if (type == "void") {
			java_expression.add("}");
		}
	}

	/**
	 * Adds a return statement to the java_expression.
	 * 
	 * @param returnValue the value that should be returned.
	 */
	public void addReturnvalue(String returnValue) {
		java_expression.add(javaWords.RETURN.getValue() + " " + returnValue + "; }");
	}

	/**
	 * Adds a Getter Method to the java_expression.
	 * 
	 * @param type      the Method should return.
	 * @param identfier of the Variable the Getter should return.
	 */
	public void addGetter(String type, String identfier) {
		java_expression.add(javaWords.PUBLIC.getValue() + " " + type + " get" + identfier + "() { "
				+ javaWords.RETURN.getValue() + " " + identfier + "; } \n");

	}

	/**
	 * Adds a Setter Method to the java_expression.
	 * 
	 * @param type      the Method should set.
	 * @param identfier for the variable the Setter you initalize.
	 */
	public void addSetter(String type, String identfier) {
		java_expression.add(javaWords.PUBLIC.getValue() + " " + javaWords.VOID.getValue() + " set" + identfier + "("
				+ type + " " + identfier + ") { this." + identfier + " = " + identfier + "; } \n");
	}

	/**
	 * @return the scope of the current Variable.
	 */
	public static int getScope() {
		try {
			SymbolTable symboltable = new SymbolTable();
			int scope = Integer.parseInt(symboltable.getAllIdentifier().get(iterationCounter)[1]);

			return scope;
		} catch (Exception e) {
			System.out.println("Error in getScope: " + e);
			return 0;
		}
	}

	/**
	 * @return the scope of the next Variable.
	 */
	public static int getNextScope() {
		try {
			SymbolTable symboltable = new SymbolTable();

			if (symboltable.getAllIdentifier().get(iterationCounter + 1) != null) {

				int scope = Integer.parseInt(symboltable.getAllIdentifier().get(iterationCounter + 1)[1]);
				return scope;

			} else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println("Error in getNextScope: " + e);
			return 0;
		}
	}

	/**
	 * @return the Identifier from the Symboltable.
	 */
	public String getId() {
		try {
			SymbolTable symboltable = new SymbolTable();
			String id = symboltable.getAllIdentifier().get(iterationCounter)[0];

			return id;
		} catch (Exception e) {
			System.out.println("Error in getId(): " + e);
			return "ERROR";
		}
	}

	/**
	 * Adds the Identifier to the java_expression and increases the iterationCounter
	 * by one.
	 * 
	 * @return the added Identifier.
	 */
	public static String addIdentifier() {
		try {
			String id;
			SymbolTable symboltable = new SymbolTable();
			id = symboltable.getAllIdentifier().get(iterationCounter)[0];
			java_expression.add(symboltable.getAllIdentifier().get(iterationCounter)[0]);
			iterationCounter++;
			return id;

		} catch (Exception e) {

			System.out.println("Error in addIdetifier: " + e);
			return "";
		}

	}

}
