package org.bachelor.transpiler.pl1transpiler.symboltable;

import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList; 

/**
 * This Enum contains als PL/I Terminalsymbols used.
 * Whenever there are new expressions added, this needs to be update
 * since the parser lexer and parser will also rely on the
 * provided symbols.
 */
public enum Pl1Symbols{
	
		 	/** The dcl. */
	 		DCL("DCL", "operator"),
		    
    		/** The declare. */
    		DECLARE("DECLARE", "operator"),
		    
    		/** The binary. */
    		BINARY("BINARY", "Arithmetic"),
		    
    		/** The proc. */
    		PROC("PROC", "type"),
		    
    		/** The procedure. */
    		PROCEDURE("PROCEDURE", "type"),
		    
    		/** The end. */
    		END("END", "type"),
		    
    		/** The bin. */
    		BIN("BIN", "type"),
		    
    		/** The fixed. */
    		FIXED("FIXED", "Arithmetic"),
		    
    		/** The float. */
    		FLOAT("FLOAT", "Arithmetic"),
		    
    		/** The character. */
    		CHARACTER("CHARACTER", "String"),
		    
    		/** The char. */
    		CHAR("CHAR", "String"),
		    
    		/** The varying. */
    		VARYING("VARYING", "type"),
		    
    		/** The var. */
    		VAR("VAR", "type"),
		    
    		/** The pic. */
    		PIC("PIC", "type"),
		    
    		/** The picture. */
    		PICTURE("PICTURE", "type"),
		    
    		/** The init. */
    		INIT("INIT", "operator"),
		    
    		/** The ptr. */
    		PTR("PTR", "type"),
		    
    		/** The decimal. */
    		DECIMAL("DECIMAL", "Arithmetic"),
		    
    		/** The dec. */
    		DEC("DEC", "Arithmetic"),
		    
    		/** The real. */
    		REAL("REAL", "Arithmetic"),
		    
    		/** The complex. */
    		COMPLEX("COMPLEX", "Arithmetic"),
		    
    		/** The unsigned. */
    		UNSIGNED("UNSIGNED", "Arithmetic"),
		    
    		/** The signed. */
    		SIGNED("SIGNED", "Arithmetic"),
		    
    		/** The area. */
    		AREA("AREA", "type"),
		    
    		/** The dimension. */
    		DIMENSION("DIMENSION", "type"),
		    
    		/** The entry. */
    		ENTRY("ENTRY", "type"),
		    
    		/** The file. */
    		FILE("FILE", "type"),
		    
    		/** The format. */
    		FORMAT("FORMAT", "type"),
		    
    		/** The graphic. */
    		GRAPHIC("GRAPHIC", "type"),
		    
    		/** The handle. */
    		HANDLE("HANDLE", "type"),
		    
    		/** The label. */
    		LABEL("LABEL", "type"),
		    
    		/** The locates. */
    		LOCATES("LOCATES", "type"),
		    
    		/** The nonvarying. */
    		NONVARYING("NONVARYING", "type"),
		    
    		/** The offset. */
    		OFFSET("OFFSET", "type"),
		    
    		/** The ordinal. */
    		ORDINAL("ORDINAL", "type"),
		    
    		/** The pointer. */
    		POINTER("POINTER", "type"),
		    
    		/** The precision. */
    		PRECISION("PRECISION", "Arithmetic"),
		    
    		/** The returns. */
    		RETURNS("RETURNS", "type"),
		    
    		/** The assembler. */
    		ASSEMBLER("ASSEMBLER", "option"),
		    
    		/** The cobol. */
    		COBOL("COBOL", "option"),
		    
    		/** The fortran. */
    		FORTRAN("FORTRAN", "option"),
		    
    		/** The main. */
    		MAIN("MAIN", "option"),
		    
    		/** The noexecops. */
    		NOEXECOPS("NOEXECOPS", "option"),
		    
    		/** The nochargraphic. */
    		NOCHARGRAPHIC("NONCHARGRAPHIC", "option"),
		    
    		/** The chargraphic. */
    		CHARGRAPHIC("CHARGRAPHIC", "option"),
		    
    		/** The nocmpat. */
    		NOCMPAT("NOCMPAT", "option"),
		    
    		/** The cmpat. */
    		CMPAT("CMPAT", "option"),
		    
    		/** The descriptor. */
    		DESCRIPTOR("DESCRIPTOR", "option"),
		    
    		/** The nodescriptor. */
    		NODESCRIPTOR("NODESCRIPTOR", "option"),
		    
    		/** The formalien. */
    		FORMALIEN("FORMALIEN", "option"),
		    
    		/** The linkage. */
    		LINKAGE("LINKAGE", "option"),
		    
    		/** The noinline. */
    		NOINLINE("NOINLINE", "option"),
		    
    		/** The inline. */
    		INLINE("INLINE", "option"),
		    
    		/** The order. */
    		ORDER("ORDER", "option"),
		    
    		/** The reorder. */
    		REORDER("REORDER", "option"),
		    
    		/** The irreducible. */
    		IRREDUCIBLE("IRREDUCIBLE", "option"),
		    
    		/** The reducable. */
    		REDUCABLE("REDUCABLE", "option"),
		    
    		/** The reetrant. */
    		REETRANT("REETRANT", "option"),
		    
    		/** The retcode. */
    		RETCODE("RETCODE", "option"),
		    
    		/** The winmain. */
    		WINMAIN("WINMAIN", "option"),
		    
    		/** The structure. */
    		STRUCTURE("STRUCUTRE", "type"),
		    
    		/** The task. */
    		TASK("TASK", "type"),
		    
    		/** The type. */
    		TYPE("TYPE", "type"),
		    
    		/** The union. */
    		UNION("UNION", "type"),
		    
    		/** The varying4. */
    		VARYING4("VARYING4", "type"),
		    
    		/** The varyingz. */
    		VARYINGZ("VARYINGZ", "type"),
		    
    		/** The widechar. */
    		WIDECHAR("WIDECHAR", "String"),
		    
    		/** The widepic. */
    		WIDEPIC("WIDEPIC", "type"),
		    
    		/** The abnormal. */
    		ABNORMAL("ABNORMAL", "type"),
		    
    		/** The aligned. */
    		ALIGNED("ALIGNED", "type"),
		    
    		/** The assignable. */
    		ASSIGNABLE("ASSIGNABLE", "type"),
		    
    		/** The automatic. */
    		AUTOMATIC("AUTOMATIC", "type"),
		    
    		/** The based. */
    		BASED("BASED", "type"),
		    
    		/** The bigendian. */
    		BIGENDIAN("BIGENDIAN", "type"),
		    
    		/** The buffered. */
    		BUFFERED("BUFFERED", "type"),
		    
    		/** The builtin. */
    		BUILTIN("BUILTIN", "type"),
		    
    		/** The byaddr. */
    		BYADDR("BYADDR", "type"),
		    
    		/** The byvalue. */
    		BYVALUE("BYVALUE", "type"),
		    
    		/** The condition. */
    		CONDITION("CONDITION", "type"),
		    
    		/** The connected. */
    		CONNECTED("CONNECTED", "type"),
		    
    		/** The controlled. */
    		CONTROLLED("CONTROLLED", "type"),
		    
    		/** The defined. */
    		DEFINED("DEFINED", "type"),
		    
    		/** The dimacross. */
    		DIMACROSS("DIMACROSS", "type"),
		    
    		/** The direct. */
    		DIRECT("DIRECT", "type"),
		    
    		/** The environment. */
    		ENVIRONMENT("ENVIRONMENT", "type"),
		    
    		/** The exclusive. */
    		EXCLUSIVE("EXCLUSIVE", "type"),
		    
    		/** The external. */
    		EXTERNAL("EXTERNAL", "type"),
		    
    		/** The generic. */
    		GENERIC("GENERIC", "type"),
		    
    		/** The hexadec. */
    		HEXADEC("HEXADEC", "type"),
		    
    		/** The ieee. */
    		IEEE("IEEE", "type"),
		    
    		/** The indfor. */
    		INDFOR("INDFOR", "type"),
		    
    		/** The initial. */
    		INITIAL("INITIAL", "type"),
		    
    		/** The inonly. */
    		INONLY("INONLY", "type"),
		    
    		/** The inout. */
    		INOUT("INOUT", "type"),
		    
    		/** The input. */
    		INPUT("INPUT", "type"),
		    
    		/** The intermal. */
    		INTERMAL("INTERMAL", "type"),
		    
    		/** The keyed. */
    		KEYED("KEYED", "type"),
		    
    		/** The like. */
    		LIKE("LIKE", "type"),
		    
    		/** The list. */
    		LIST("LIST", "type"),
		    
    		/** The littleendian. */
    		LITTLEENDIAN("LITTLEENDIAN", "type"),
		    
    		/** The nonassignable. */
    		NONASSIGNABLE("NONASSIGNABLE", "type"),
		    
    		/** The nonconnected. */
    		NONCONNECTED("NONCONNECTED", "type"),
		    
    		/** The normal. */
    		NORMAL("NORMAL", "type"),
		    
    		/** The optional. */
    		OPTIONAL("OPTIONAL", "type"),
		    
    		/** The options. */
    		OPTIONS("OPTIONS", "type"),
		    
    		/** The outonly. */
    		OUTONLY("OUTONLY", "type"),
		    
    		/** The output. */
    		OUTPUT("OUTPUT", "type"),
		    
    		/** The parameter. */
    		PARAMETER("PARAMETER", "type"),
		    
    		/** The position. */
    		POSITION("POSITION", "type"),
		    
    		/** The print. */
    		PRINT("PRINT", "type"),
		    
    		/** The record. */
    		RECORD("RECORD", "type"),
		    
    		/** The sequential. */
    		SEQUENTIAL("SEQUENTIAL", "type"),
		    
    		/** The static. */
    		STATIC("STATIC", "type"),
		    
    		/** The stream. */
    		STREAM("STREAM", "type"),
		    
    		/** The unaligned. */
    		UNALIGNED("UNALIGNED", "type"),
		    
    		/** The unbuffered. */
    		UNBUFFERED("UNBUFFERED", "type"),
		    
    		/** The update. */
    		UPDATE("UPDATE", "type"),
		    
    		/** The value. */
    		VALUE("VALUE", "Arithmetic"),
		    
    		/** The variable. */
    		VARIABLE("VARIABLE", "type"),
		    
    		/** The xmlattr. */
    		XMLATTR("XMLATTR", "type"),
		    
    		/** The recursive. */
    		RECURSIVE("RECURSIVE", "type"),
		    
    		/** The xmlomit. */
    		XMLOMIT("XMLOMIT", "type"),
			
			/** The call. */
			CALL("CALL", "invoke"),
			
			/** The return. */
			RETURN("RETURN", "operator"),
			
			/** The goto. */
			GOTO("GO TO", "invoke"),
			
			/** The stop. */
			STOP("STOP", "operator"),
			
			/** The reserves. */
			RESERVES("RESERVES", "operator"),
			
			/** The package. */
			PACKAGE("PACKAGE", "type"),
			
			/** The exports. */
			EXPORTS("EXPORTS", "operator"),
			
			/** The begin. */
			BEGIN("BEGIN", "operator"),
			
			/** The if. */
			IF("IF", "operator"),
			
			/** The then. */
			THEN("THEN", "operator"),
			
			/** The else. */
			ELSE("ELSE", "operator"),
			
			/** The to. */
			TO("TO", "operator"),
			
			/** The by. */
			BY("BY", "operator"),
			
			/** The repeat. */
			REPEAT("REPEAT", "operator"),
			
			/** The upthru. */
			UPTHRU("UPTHRU", "operator"),
			
			/** The downthru. */
			DOWNTHRU("DOWNTHRU", "operator"),
			
			/** The do. */
			DO("DO", "operator"),
			
			/** The loop. */
			LOOP("LOOP", "operator"),
			
			/** The while. */
			WHILE("WHILE", "operator"),
			
			/** The until. */
			UNTIL("UNTIL", "operator"),
			
			/** The exit. */
			EXIT("EXIT", "operator"),
			
			/** The display. */
			DISPLAY("DISPLAY", "operator"),
			
			/** The reply. */
			REPLY("REPLY", "operator"),
			
			/** The read. */
			READ("READ", "operator"),
			
			/** The read. */
			WRITE("WRITE", "operator"),
			
			/** The read. */
			FROM("FROM", "operator"),
			
			/** The into. */
			INTO("INTO", "operator");
	
	 	/** The symbol. */
	 	private final String symbol;
	    
    	/** The category. */
    	private final String category;

	    /**
    	 * Instantiates a new pl 1 symbols.
    	 *
    	 * @param symbol the symbol
    	 * @param category the category
    	 */
    	Pl1Symbols(String symbol, String category) {
	        this.symbol = symbol;
	        this.category = category;
	    }

	    /**
    	 * Gets the symbol.
    	 *
    	 * @return A Symbol if it's set by the Construcutor.
    	 */
	    public String getSymbol() {
	        return symbol;
	    }
	    
	    /**
    	 * This Method is used to search for specific 
    	 * symobl in the Enum. Mainly used by the SymbolTable.
    	 *
    	 * @param symbol symbol that should be found in the Enmus.
    	 * @return the Enum that should be found.
    	 * @throws UnsupportedCharsetException the unsupported charset exception
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
