package pl1transpiler.SymbolHandeling.Java;

public enum JavaMacros {
	//Static Code
	PACKAGE("package pl1transpiler.output;"),
	
	IMPORTS("import java.math.BigDecimal; "
			+ "import java.util.HashMap; \n "
			+ "import java.util.HashMap; \n "
			+ "import java.util.regex.Matcher; \n "
			+ "import java.util.regex.Pattern;"
			+ "import pl1transpiler.SymbolHandeling.Pl1.PictureMapper;"),
	
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

	PICTURE_CLASS("class PICTURE implements Datatype{ \n"
			+ "Pattern pattern; \n"
			+ "String content; \n"
			+ "public PICTURE(String regex) { \n"
			+ "pattern = Pattern.compile(regex); } \n"
			+ "public void init(String content) { \n"
			+ "Matcher matcher = pattern.matcher(content); \n"
			+ "if(matcher.matches()) { \n"
			+ "this.content = content; } \n"
			+ "else return; } }\n"),
	
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
	PICTURE("PICTURE");

	private final String symbol;
    
	JavaMacros(String symbol) {
        this.symbol = symbol;
    }

    public String getValue() {
        return symbol + " ";
    }
}
