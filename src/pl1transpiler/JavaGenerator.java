package pl1transpiler;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.JRootPane;

import pl1transpiler.Pl1Parser.Node;
import pl1transpiler.Pl1Parser.Pl1Parser;
import pl1transpiler.Pl1Parser.SimpleNode;
import pl1transpiler.SymbolHandeling.Java.JavaMacros;
import pl1transpiler.SymbolHandeling.Pl1.SymbolTable;

public class JavaGenerator {
	
	static int iterationCounter = 0;
	SymbolTable symboltable = new SymbolTable();
	static ArrayList<String> java_expression = new ArrayList<String>();
	Pl1Parser pl1Parser;
	String Major;
	JavaMacros javaWords;
	
	public JavaGenerator() {
		
	}
	
	public JavaGenerator(Pl1Parser pp) {
		this.pl1Parser = pp;
	}
	
	public String concatExpression() {
		try {
				
				if(java_expression.size() != 0) {
					String listString = java_expression.stream()
			                .map(Object::toString)
			                .collect(Collectors.joining(""));
					listString = 
							javaWords.PACKAGE.getValue() + "\n"
							+ javaWords.IMPORTS.getValue() + "\n"
							+ javaWords.MAIN_CLASS.getValue() +  listString 
							+ javaWords.MAIN_METHOD.getValue() +" }\n"
							+ javaWords.DATATYPE_INTERFACE.getValue() + "\n"
							+javaWords.PICTURE_CLASS.getValue() + "\n"
							+ javaWords.PL1_CHAR_CLASS.getValue() + "\n"
							;
					return listString;
					
				}
				
				return "Java Expression is 0";
				
		}
		
		catch (Exception e) {
            e.printStackTrace();
        }
		
		return "Unhandled Error";
	}
	
	public void createExpression(Node root) {
		
		Node currentroot = root.jjtGetChild(0);
		String id = getId();
		int currentScope = getScope();
		
		for(int i = 0;i<root.jjtGetNumChildren();i++) {
			if(root.jjtGetChild(i).toString() == "VAR") {
				for(int j = 0; j<root.jjtGetChild(i).jjtGetNumChildren(); j++) {
					createStructure(root.jjtGetChild(i).jjtGetChild(j));
					
				}
			}
		}
		}
		
	public void createStructure(Node root) {
		int currentScope = getScope();
		
		try {
			if(root.toString() == "TYPE") {
				addType(root);
			}	
			else if(root.toString() == "MINOR" && currentScope == 1) {
				addClass();
				for(int i = 0; i<root.jjtGetNumChildren(); i++) {
					createStructure(root.jjtGetChild(i));
				}
			}
			else if(root.toString() == "MINOR" && currentScope > 1) {
				for(int i = 0; i<root.jjtGetNumChildren(); i++) {
					createStructure(root.jjtGetChild(i));
				}
			}
			else {
				return;
			}
		}catch(Exception e) {
			System.out.println("Error in createStructure() " + e);
		}
		}
	
	/**
	 * @TODO String in char mit to String
	 * @param root
	 */
	public void addType(Node root){
		try {
		Node type = root.jjtGetChild(0);
		
			if (type.toString() == "String") {
				String idString;
				
					java_expression.add(javaWords.PRIVATE.getValue() + " PL1_CHAR ");
					idString = addIdentifier();
					java_expression.add(" = " + javaWords.NEW.getValue() + " PL1_CHAR(" + pl1Parser.getCharLength() + ")");
					java_expression.add("; \n");
				
			}
			else if(type.toString() == "Arithmetic") {
				addArithmetic(type);
			}
			else if(type.toString() == "PictureExpression") {
				addPictureExpression();
			}
		} catch(Exception e) {
			System.out.println("Error in addType() : " + e);
		}
	}
	
	public void addPictureExpression() {
	
		String picRegex = pl1Parser.getPictureAttirbute().replaceAll("\'", "");
		
		java_expression.add(javaWords.PICTURE.getValue() + " ");
		this.addIdentifier();
		java_expression.add(" = " + javaWords.NEW.getValue());
		java_expression.add(" " + javaWords.PICTURE.getValue() + " (\"" + picRegex + "\");\n");
	}
	
	public void addObject(String className) {
		java_expression.add(className + " " + className + "_ = " + javaWords.NEW.getValue() + " " + className + "();");
	}
	
	public void addArithmetic(Node root) {
		
		Node decimalType = root.jjtGetChild(root.jjtGetNumChildren()-1).jjtGetChild(0);
		
		if(root.jjtGetChild(0).toString() == "Binary") {
			String idInt;
			if(java_expression.contains(Major)){
				
				java_expression.add(javaWords.PRIVATE.getValue() + " " + javaWords.INT.getValue());
				idInt = addIdentifier();
				java_expression.add("; \n");
				addSetter(javaWords.INT.getValue(), idInt);
				addGetter(javaWords.INT.getValue(), idInt);
			}
			else {
				java_expression.add(javaWords.INT.getValue());
				idInt = addIdentifier();
				java_expression.add("; \n");
			}
		
		}
		else {
			if(root.jjtGetNumChildren() > 2 && root.jjtGetChild(0).toString() != "Real") {
				addPrefixDecimal(root);
			}
			else {
			
			switch(decimalType.toString()) {
					case "Int": 
							String idInt;
							if(java_expression.contains(Major)){
								java_expression.add(javaWords.PRIVATE.getValue() + " " + javaWords.INT.getValue());
								idInt = addIdentifier();
								java_expression.add("; \n");
								addSetter(javaWords.INT.getValue(), idInt);
								addGetter(javaWords.INT.getValue(), idInt);
							}
							else {
								java_expression.add(javaWords.INT.getValue() + " ");
								idInt = addIdentifier();
								java_expression.add("; \n");
							}
						
						break;
						
					case "Double" : 
						String idDouble;
						if(java_expression.contains(Major)){
							java_expression.add(javaWords.PRIVATE.getValue() + " " + javaWords.DOUBLE.toString());
							idDouble = addIdentifier();
							java_expression.add("; \n");
							addSetter(javaWords.DOUBLE.getValue(), idDouble);
							addGetter(javaWords.DOUBLE.getValue(), idDouble);
						}
						else {
							java_expression.add(javaWords.DOUBLE.toString());
							idDouble = addIdentifier();
							java_expression.add("; \n");
						}
						
						break;
					default: System.out.println("In default case");
						break;
				}
			}
		}
		
	}
	
	public void addPrefixDecimal(Node root) {
		
		for(int i = 0; i<root.jjtGetNumChildren();i++) {
			switch(root.jjtGetChild(i).toString()) {
				case "Complex" :
					addComplex();
					break;
				case "Fixed" :
					addBigDecimal();
					break;
				case "Float" :
					addFloat();
					break;
				case "size" :
					
					break;
				default: System.out.println("Error in addPrefixDecimal Methode");
			}
		}
	}
	
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
	
	public void addBigDecimal() {
		
		if(!(pl1Parser.getValue().contains(","))){
			String p = pl1Parser.getValue();
			int precision = (int)Math.pow(10, Double.parseDouble(p)-1);
			
			if(p.equals("1")) {
				precision = (int)Math.pow(10, 0);
			}
			
			java_expression.add(javaWords.BIGDECIMAL.getValue() + " ");
			this.addIdentifier();
			java_expression.add(" = " + javaWords.NEW.getValue() + " " + javaWords.BIGDECIMAL.getValue() + "(\"" + precision + "," + 0 + "\");");
		}
		else {
			//uses the parse tree value and safes the first digit and the second after the ,
			String p = pl1Parser.getValue().substring(0, pl1Parser.getValue().indexOf(","));
			String q = pl1Parser.getValue().substring(pl1Parser.getValue().indexOf(","),
					pl1Parser.getValue().length()).replaceAll(",", "");
			int precision; 
			int quantum;
			
			//Adds BigDecimal
			java_expression.add(javaWords.BIGDECIMAL.getValue());
			precision = (int)Math.pow(10, Double.parseDouble(p)-1);
			quantum = (int)Math.pow(10, Double.parseDouble(q)-1);
			
			if(p.equals("1")) {
				precision = (int)Math.pow(10, 0);
			}
			if(q.equals("1")) {
				quantum = (int)Math.pow(10, 0);
			}
			this.addIdentifier();
			java_expression.add(" = "+ javaWords.NEW.getValue() + " " + javaWords.BIGDECIMAL.getValue() + "(\"" + precision + "," + quantum + "\");");
		}
	}
	
	public void addFloat() {
		String idFloat;
		if(java_expression.contains(Major)){
			java_expression.add(javaWords.PRIVATE.getValue() + " " + javaWords.FLOAT.getValue());
			idFloat = addIdentifier();
			java_expression.add("; \n");
			addSetter(javaWords.FLOAT.getValue(), idFloat);
			addGetter(javaWords.FLOAT.getValue(), idFloat);
		}
		else {
			java_expression.add(javaWords.FLOAT.getValue());
			idFloat = addIdentifier();
			java_expression.add("; \n");
		}
	}
	
	public String addClass() {
		try {
		addObject(symboltable.getAllIdentifier().get(iterationCounter)[0]);
		Major = getId();
		System.out.println("Major: " + Major);
		java_expression.add("} \n" + javaWords.CLASS.getValue() + " ");
		String id = addIdentifier();	
		java_expression.add(" { \n ");
		return id;
		}
		catch(Exception e) {
			System.out.println("Error in addClass(): " + e);
			return "";
		}
	}
	
	public void addMethod(String type, String id) {
		java_expression.add(javaWords.PUBLIC.getValue() + " " + type + " " + id + "() { \n");
		if(type == "void") {
			java_expression.add("}");
		}
	}
	
	public void addReturnvalue(String returnValue) {
		java_expression.add(javaWords.RETURN.getValue() + " " + returnValue + "; }");
	}
	
	public void addGetter(String type, String identfier) {
		java_expression.add(javaWords.PUBLIC.getValue() + " " + type + " get" + identfier + "() { " + javaWords.RETURN.getValue() + " " + identfier + "; } \n");
		
	}
	
	public void addSetter(String type, String identfier) {
		java_expression.add(javaWords.PUBLIC.getValue() + " " + javaWords.VOID.getValue() + " set" + identfier + "("+ type + " " + identfier +") { this." + identfier + " = " + identfier + "; } \n");
	}
	
	public static int getScope() {
		try {
		SymbolTable symboltable = new SymbolTable();
		int scope = Integer.parseInt(symboltable.getAllIdentifier().get(iterationCounter)[1]);
		
		return scope;
		}
		catch(Exception e) {
			System.out.println("Error in getScope: " + e);
			return 0;
		}
	}
	
	public static int getNextScope() {
		try {
		SymbolTable symboltable = new SymbolTable();
		
		if(symboltable.getAllIdentifier().get(iterationCounter+1) != null) {
			
			int scope = Integer.parseInt(symboltable.getAllIdentifier().get(iterationCounter+1)[1]);
			return scope;
			
		}
		else {
			return 0;
		}
		}
		catch(Exception e) {
			System.out.println("Error in getNextScope: " + e);
			return 0;
		}
	}
	
	public String getId() {
		try {
		SymbolTable symboltable = new SymbolTable();
		String id = symboltable.getAllIdentifier().get(iterationCounter)[0];
		
		return id;
		}
		catch(Exception e) {
			System.out.println("Error in getId(): " + e);
			return "ERROR";
		}
	}
	
	public static String addIdentifier(){
		try {
			String id;
			SymbolTable symboltable = new SymbolTable();
			id = symboltable.getAllIdentifier().get(iterationCounter)[0];
			java_expression.add(symboltable.getAllIdentifier().get(iterationCounter)[0]);
			iterationCounter++;
			return id;
			
		} catch(Exception e) {
			
			System.out.println("Error in addIdetifier: " + e);
			return "";
		}
		
	}
	
	
	
	
}
