package pl1transpiler.tools.Java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




public enum template {
	//Static Code
	PACKAGE("package pl1transpiler.output;"),
	
	IMPORTS("import java.math.BigDecimal; "
			+ "import java.util.HashMap; \n "
			+ "import java.util.HashMap; \n "
			+ "import java.util.regex.Matcher; \n "
			+ "import java.util.regex.Pattern;"),
	
	DATATYPE_INTERFACE("interface Datatype { \n"
			+ "public void init(String content); \n"
			+ "}; \n"),
	
	MAIN_CLASS("public class Main {"),

	MAIN_METHOD("public static void main(String[] args) { };"),
	
	PL1_CHAR_CLASS("class PL1_CHAR implements Datatype{ "
			+ "char[] varname; "
			+ "public PL1_CHAR(int limit) { \n"
			+ "varname =  new char[limit]; } \n"
			+ "@Override public String toString() { \n"
			+ " String string = new String(varname); \n"
			+ "return string; } \n"
			+ "public void init(String varname) { \n"
			+ "for(int i = 0; i<this.varname.length; i++) { \n"
			+ "this.varname[i] = varname.charAt(i); }}} \n"),

	PICTURE_CLASS("class PICTURE {\r\n"
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
	
	//Words
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
	BINARY("BINARY");

	private final String symbol;
    
	template(String symbol) {
        this.symbol = symbol;
    }

	/**
	 * @return the value of the Enum.
	 */
    public String getValue() {
        return symbol + " ";
    }
}
