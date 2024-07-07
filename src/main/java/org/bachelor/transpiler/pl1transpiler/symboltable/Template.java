/*
 * 
 */
package org.bachelor.transpiler.pl1transpiler.symboltable;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bachelor.transpiler.pl1transpiler.scanner.*;

// TODO: Auto-generated Javadoc
/**
 * This Enum contains all the Java macros which should later are mapped to the
 * Java target source code. This may contains custom written Java Code or just
 * plain Java symbols which are available by this.
 */
public enum Template {

	/** The package. */
	PACKAGE("package "),

	/** The picture imports. */
	PICTURE_IMPORTS("import java.util.regex.Matcher; \n " + "import java.util.regex.Pattern;"),

	/** The binary imports. */
	BINARY_IMPORTS("import java.math.BigDecimal; "),

	/** The imports. */
	IMPORTS("import java.util.HashMap; \n " + "import java.util.HashMap; \n "
			+ "import transpiled-sources.PLI Dependencies;"),

	/** The Datatype interface. */
	DATATYPE_INTERFACE("interface Datatype { \n" + "public void init(String content); \n" + "}; \n"),

	/** The main class. */
	MAIN_CLASS("public class "),

	/** The main method. */
	MAIN_METHOD("public static void main(String[] args) { };"),

	/** The pl1 char class. */
	PL1_CHAR_CLASS("class CHAR{ " + "char[] varname; " + "public CHAR(int limit) { \n"
			+ "varname =  new char[limit]; } \n" + "@Override public String toString() { \n"
			+ " String string = new String(varname); \n" + "return string; } \n"
			+ "public CHAR init(String varname) { \n" + "for(int i = 0; i<this.varname.length; i++) { \n"
			+ "this.varname[i] = varname.charAt(i); " + "return this;}}} \n"),

	/** The picture class. */
	PICTURE_CLASS("class PICTURE implements Datatype{\r\n" + "	Pattern pattern;\r\n" + "	String content;\r\n"
			+ "\r\n" + "	public PICTURE(String regex) {\r\n" + "		pattern = Pattern.compile(regex);\r\n"
			+ "	}\r\n" + "\r\n" + "	public void init(String content) {\r\n"
			+ "		Matcher matcher = pattern.matcher(content);\r\n" + "		if (matcher.matches()) {\r\n"
			+ "			this.content = content;\r\n" + "		}\r\n" + "	}\r\n" + "}"),

	/** The binary class. */
	BINARY_CLASS("class BINARY implements Datatype {\r\n" + "	byte[] content;\r\n" + "	\r\n"
			+ "	public BINARY(int limit) {\r\n" + "		content = new byte[limit];\r\n" + "	}\r\n" + "	\r\n"
			+ "	public void init(String content) {\r\n" + "		for(int i = 0; i<this.content.length;i++) {\r\n"
			+ "			this.content[i] = (byte)content.charAt(i);\r\n" + "		}\r\n" + "	}\r\n" + "}"),

	/** The complex class. */
	COMPLEX_CLASS("" + "public class COMPLEX {\n" + "double real;\n" + "double imagnary;\n" + "	public COMPLEX() {\n"
			+ "	}\n" + "}\n"),

	/** The decimal class. */
	DECIMAL_CLASS("import java.math.BigDecimal;\n" + "\n" + "public class DECIMAL {\n" + "	BigDecimal content;\n"
			+ "	\n" + "	public DECIMAL() {\n" + "		\n" + "	}\n" + "	\n"
			+ "	public DECIMAL(int integer, int factorial) {\n"
			+ "		String length = Integer.valueOf(factorial) == null ? integer + \"\" : integer + \".\" + factorial;\n"
			+ "		this.content = new BigDecimal(length);\n" + "	}\n" + "	\n" + "	public DECIMAL(int integer) {\n"
			+ "		String length = integer + \"\";\n" + "		this.content = new BigDecimal(length);\n" + "	}\n"
			+ "	\n" + "	public DECIMAL(int integer, int factorial, int value) {\n" + "		//TODO\n" + "	}\n"
			+ "	\n" + "	public DECIMAL init(int value) {\n" + "		this.content.valueOf((double)value);\n"
			+ "		return this;\n" + "	}\n" + "	\n" + "	private double toNumeric() {\n"
			+ "		return this.content.doubleValue();\n" + "	}\n" + "}"),

	/** The complex. */
	// Words
	COMPLEX("COMPLEX"),

	/** The char object. */
	CHAR_OBJECT("CHAR"),

	/** The object. */
	OBJECT("Object"),

	/** The file. */
	FILE("File"),

	/** The public. */
	PUBLIC("public"),

	/** The private. */
	PRIVATE("private"),

	/** The double. */
	DOUBLE("double"),

	/** The class. */
	CLASS("class"),

	/** The bigdecimal. */
	BIGDECIMAL("BigDecimal"),

	/** The float. */
	FLOAT("float"),

	/** The new. */
	NEW("new"),

	/** The int. */
	INT("int"),

	/** The return. */
	RETURN("return"),

	/** The void. */
	VOID("void"),

	/** The char. */
	CHAR("char"),

	/** The string. */
	STRING("String"),

	/** The picture. */
	PICTURE("PICTURE"),

	/** The binary. */
	BINARY("BINARY"),

	/** The if. */
	IF("if"),

	/** The else. */
	ELSE("else"),

	/** The else if. */
	ELSE_IF("else if"),

	/** The while. */
	WHILE("while"),

	/** The for. */
	FOR("for"),

	/** The do. */
	DO("do"),

	/** The continue. */
	CONTINUE("continue"),

	/** The sysout. */
	SYSOUT("System.out.println"),

	/** The sysin. */
	SYSIN("System.console().readLine()"),

	/** The init. */
	INIT(".init"),

	/** The null. */
	NULL("null"),

	/** The decimal. */
	DECIMAL("DECIMAL"),

	/** The decimal annotation. */
	DECIMAL_ANNOTATION("@Decimal"),

	/** The tonumeric. */
	TONUMERIC("toNumeric"),

	/** The break. */
	BREAK("break"),

	/** The calc. */
	CALC(".calc"),

	/** The add. */
	ADD(" + "),
	
	/** The multiply. */
	MULTIPLY(" * "),
	
	/** The divide. */
	DIVIDE(" / "),
	
	/** The substract. */
	SUBSTRACT(" - "),
	
	/** The assign. */
	ASSIGN(" = "),

	/** The try. */
	TRY("try"),

	/** The catch. */
	CATCH("catch"),

	/** The scanner. */
	SCANNER("Scanner readFile = new Scanner");

	/** The symbol. */
	private final String symbol;

	/**
	 * Instantiates a new template.
	 *
	 * @param symbol the symbol
	 */
	private Template(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * This will return the String which is represent in the class by specific
	 * identifier.
	 *
	 * @return the value of the Enum.
	 */
	public String getValue() {
		return symbol;
	}
}
