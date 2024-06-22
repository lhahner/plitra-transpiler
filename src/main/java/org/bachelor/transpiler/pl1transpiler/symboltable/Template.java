package org.bachelor.transpiler.pl1transpiler.symboltable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bachelor.transpiler.pl1transpiler.scanner.*;

public enum Template {
	
	//Static Code
	PACKAGE("package "),
	
	PICTURE_IMPORTS("import java.util.regex.Matcher; \n "
			+ "import java.util.regex.Pattern;"),
	BINARY_IMPORTS("import java.math.BigDecimal; "),
	IMPORTS( "import java.util.HashMap; \n "
			+ "import java.util.HashMap; \n "
			+ "import transpiled-sources.PLI Dependencies;"),
	
	DATATYPE_INTERFACE("interface Datatype { \n"
			+ "public void init(String content); \n"
			+ "}; \n"),
	
	MAIN_CLASS("public class "),

	MAIN_METHOD("public static void main(String[] args) { };"),
	
	PL1_CHAR_CLASS("class CHAR implements Datatype{ "
			+ "char[] varname; "
			+ "public CHAR(int limit) { \n"
			+ "varname =  new char[limit]; } \n"
			+ "@Override public String toString() { \n"
			+ " String string = new String(varname); \n"
			+ "return string; } \n"
			+ "public void init(String varname) { \n"
			+ "for(int i = 0; i<this.varname.length; i++) { \n"
			+ "this.varname[i] = varname.charAt(i); }}} \n"),

	PICTURE_CLASS("class PICTURE implements Datatype{\r\n"
			+ "	Pattern pattern;\r\n"
			+ "	String content;\r\n"
			+ "\r\n"
			+ "	public PICTURE(String regex) {\r\n"
			+ "		pattern = Pattern.compile(regex);\r\n"
			+ "	}\r\n"
			+ "\r\n"
			+ "	public void init(String content) {\r\n"
			+ "		Matcher matcher = pattern.matcher(content);\r\n"
			+ "		if (matcher.matches()) {\r\n"
			+ "			this.content = content;\r\n"
			+ "		}\r\n"
			+ "	}\r\n"
			+ "}"),
	
	BINARY_CLASS("class BINARY implements Datatype {\r\n"
			+ "	byte[] content;\r\n"
			+ "	\r\n"
			+ "	public BINARY(int limit) {\r\n"
			+ "		content = new byte[limit];\r\n"
			+ "	}\r\n"
			+ "	\r\n"
			+ "	public void init(String content) {\r\n"
			+ "		for(int i = 0; i<this.content.length;i++) {\r\n"
			+ "			this.content[i] = (byte)content.charAt(i);\r\n"
			+ "		}\r\n"
			+ "	}\r\n"
			+ "}"),
	COMPLEX_CLASS(""
			+ "public class COMPLEX {\n"
			+ "double real;\n"
			+ "double imagnary;\n"
			+ "	public COMPLEX() {\n"
			+ "	}\n"
			+ "}\n"),
	//Words
	COMPLEX("COMPLEX"),
	CHAR_OBJECT("CHAR"),
	OBJECT("Object"),
	FILE("File"),
	PUBLIC("public"),
	PRIVATE("private"),
	DOUBLE("double"),
	CLASS("class"),
	BIGDECIMAL("BigDecimal"),
	FLOAT("float"),
	NEW("new"),
	INT("int"),
	RETURN("return"),
	VOID("void"),
	CHAR("char"),
	STRING("String"),
	PICTURE("PICTURE"),
	BINARY("BINARY"),
	IF("if"),
	ELSE("else"),
	ELSE_IF("else if"),
	WHILE("while"),
	FOR("for"),
	DO("do"),
	CONTINUE("continue");

	private final String symbol;
	
	Template(String symbol) {
        this.symbol = symbol;
    }

	/**
	 * @return the value of the Enum.
	 */
    public String getValue() {
        return symbol + " ";
    }
}
