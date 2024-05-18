package org.bachelor.transpiler.pl1transpiler.symboltable;

import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList; 

public enum Pl1Symbols{
	
		 	DCL("DCL", "operator"),
		    DECLARE("DECLARE", "operator"),
		    BINARY("BINARY", "Arithmetic"),
		    PROC("PROC", "type"),
		    PROCEDURE("PROCEDURE", "type"),
		    END("END", "type"),
		    BIN("BIN", "type"),
		    FIXED("FIXED", "Arithmetic"),
		    FLOAT("FLOAT", "Arithmetic"),
		    CHARACTER("CHARACTER", "String"),
		    CHAR("CHAR", "String"),
		    VARYING("VARYING", "type"),
		    VAR("VAR", "type"),
		    PIC("PIC", "type"),
		    PICTURE("PICTURE", "type"),
		    INIT("INIT", "operator"),
		    PTR("PTR", "type"),
		    DECIMAL("DECIMAL", "Arithmetic"),
		    DEC("DEC", "Arithmetic"),
		    REAL("REAL", "Arithmetic"),
		    COMPLEX("COMPLEX", "Arithmetic"),
		    UNSIGNED("UNSIGNED", "Arithmetic"),
		    SIGNED("SIGNED", "Arithmetic"),
		    AREA("AREA", "type"),
		    DIMENSION("DIMENSION", "type"),
		    ENTRY("ENTRY", "type"),
		    FILE("FILE", "type"),
		    FORMAT("FORMAT", "type"),
		    GRAPHIC("GRAPHIC", "type"),
		    HANDLE("HANDLE", "type"),
		    LABEL("LABEL", "type"),
		    LOCATES("LOCATES", "type"),
		    NONVARYING("NONVARYING", "type"),
		    OFFSET("OFFSET", "type"),
		    ORDINAL("ORDINAL", "type"),
		    POINTER("POINTER", "type"),
		    PRECISION("PRECISION", "Arithmetic"),
		    RETURNS("RETURNS", "type"),
		    ASSEMBLER("ASSEMBLER", "option"),
		    COBOL("COBOL", "option"),
		    FORTRAN("FORTRAN", "option"),
		    MAIN("MAIN", "option"),
		    NOEXECOPS("NOEXECOPS", "option"),
		    NOCHARGRAPHIC("NONCHARGRAPHIC", "option"),
		    CHARGRAPHIC("CHARGRAPHIC", "option"),
		    NOCMPAT("NOCMPAT", "option"),
		    CMPAT("CMPAT", "option"),
		    DESCRIPTOR("DESCRIPTOR", "option"),
		    NODESCRIPTOR("NODESCRIPTOR", "option"),
		    FORMALIEN("FORMALIEN", "option"),
		    LINKAGE("LINKAGE", "option"),
		    NOINLINE("NOINLINE", "option"),
		    INLINE("INLINE", "option"),
		    ORDER("ORDER", "option"),
		    REORDER("REORDER", "option"),
		    IRREDUCIBLE("IRREDUCIBLE", "option"),
		    REDUCABLE("REDUCABLE", "option"),
		    REETRANT("REETRANT", "option"),
		    RETCODE("RETCODE", "option"),
		    WINMAIN("WINMAIN", "option"),
		    STRUCTURE("STRUCUTRE", "type"),
		    TASK("TASK", "type"),
		    TYPE("TYPE", "type"),
		    UNION("UNION", "type"),
		    VARYING4("VARYING4", "type"),
		    VARYINGZ("VARYINGZ", "type"),
		    WIDECHAR("WIDECHAR", "String"),
		    WIDEPIC("WIDEPIC", "type"),
		    ABNORMAL("ABNORMAL", "type"),
		    ALIGNED("ALIGNED", "type"),
		    ASSIGNABLE("ASSIGNABLE", "type"),
		    AUTOMATIC("AUTOMATIC", "type"),
		    BASED("BASED", "type"),
		    BIGENDIAN("BIGENDIAN", "type"),
		    BUFFERED("BUFFERED", "type"),
		    BUILTIN("BUILTIN", "type"),
		    BYADDR("BYADDR", "type"),
		    BYVALUE("BYVALUE", "type"),
		    CONDITION("CONDITION", "type"),
		    CONNECTED("CONNECTED", "type"),
		    CONTROLLED("CONTROLLED", "type"),
		    DEFINED("DEFINED", "type"),
		    DIMACROSS("DIMACROSS", "type"),
		    DIRECT("DIRECT", "type"),
		    ENVIRONMENT("ENVIRONMENT", "type"),
		    EXCLUSIVE("EXCLUSIVE", "type"),
		    EXTERNAL("EXTERNAL", "type"),
		    GENERIC("GENERIC", "type"),
		    HEXADEC("HEXADEC", "type"),
		    IEEE("IEEE", "type"),
		    INDFOR("INDFOR", "type"),
		    INITIAL("INITIAL", "type"),
		    INONLY("INONLY", "type"),
		    INOUT("INOUT", "type"),
		    INPUT("INPUT", "type"),
		    INTERMAL("INTERMAL", "type"),
		    KEYED("KEYED", "type"),
		    LIKE("LIKE", "type"),
		    LIST("LIST", "type"),
		    LITTLEENDIAN("LITTLEENDIAN", "type"),
		    NONASSIGNABLE("NONASSIGNABLE", "type"),
		    NONCONNECTED("NONCONNECTED", "type"),
		    NORMAL("NORMAL", "type"),
		    OPTIONAL("OPTIONAL", "type"),
		    OPTIONS("OPTIONS", "type"),
		    OUTONLY("OUTONLY", "type"),
		    OUTPUT("OUTPUT", "type"),
		    PARAMETER("PARAMETER", "type"),
		    POSITION("POSITION", "type"),
		    PRINT("PRINT", "type"),
		    RECORD("RECORD", "type"),
		    SEQUENTIAL("SEQUENTIAL", "type"),
		    STATIC("STATIC", "type"),
		    STREAM("STREAM", "type"),
		    UNALIGNED("UNALIGNED", "type"),
		    UNBUFFERED("UNBUFFERED", "type"),
		    UPDATE("UPDATE", "type"),
		    VALUE("VALUE", "Arithmetic"),
		    VARIABLE("VARIABLE", "type"),
		    XMLATTR("XMLATTR", "type"),
		    RECURSIVE("RECURSIVE", "type"),
		    XMLOMIT("XMLOMIT", "type"),
			CALL("CALL", "invoke"),
			RETURN("RETURN", "operator"),
			GOTO("GO TO", "invoke"),
			STOP("STOP", "operator"),
			EXIT("EXIT", "operator");
	
	 	private final String symbol;
	    private final String category;

	    Pl1Symbols(String symbol, String category) {
	        this.symbol = symbol;
	        this.category = category;
	    }

	    /**
	     * @return A Symbol if it's set by the Construcutor.
	     */
	    public String getSymbol() {
	        return symbol;
	    }
	    
	    /**
	     * @param symbol that should be found in the Enmus.
	     * @return the Enum that should be found.
	     */
	    public static Pl1Symbols findBySymbol(String symbol) throws UnsupportedCharsetException{
	    	for (Pl1Symbols s : Pl1Symbols.values()) {
	            if (s.getSymbol().equalsIgnoreCase(symbol)) {
	                return s;
	            }
	        }
	    	return null;
	    }
}
