package org.bachelor.transpiler.pl1transpiler.symboltable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.charset.UnsupportedCharsetException;
import javax.management.AttributeNotFoundException;

import org.bachelor.transpiler.pl1transpiler.mapper.JavaGenerator;
import org.bachelor.transpiler.pl1transpiler.symboltable.Pl1Symbols;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class Pl1SymbolsTests {
	
	Pl1Symbols pl1_symbols;
	
	@Test
	@DisplayName("findBySymbol")
	void findBySymbol_exactSymbolFound() {
	String[] shouldWork = {
			"DCL", 
			"DECLARE", 
			"LIKE", 
			"OPTIONAL", 
			"VALUE"};
	Pl1Symbols[] expectedResults = {
			pl1_symbols.DCL,
			pl1_symbols.DECLARE,
			pl1_symbols.LIKE,
			pl1_symbols.OPTIONAL,
			pl1_symbols.VALUE
			};
	for(int i = 0; i<expectedResults.length;i++)
		assertEquals(expectedResults[i], pl1_symbols.findBySymbol(shouldWork[i]));
	
	String[] shouldNotWork = {"declere", "LIKEE", "1", "dcl1"};
	for(String item: shouldNotWork)
		assertEquals(null, pl1_symbols.findBySymbol(item));
	}
}
