package org.bachelor.transpiler.pl1transpiler.symboltable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.charset.UnsupportedCharsetException;
import javax.management.AttributeNotFoundException;

import org.bachelor.transpiler.pl1transpiler.mapper.JavaGenerator;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class SymbolTableTests {

	SymbolTable symbol_table;
	@BeforeEach
	void init(){
		symbol_table = new SymbolTable();
	}
	
	@Test
	@DisplayName("insertId")
	void insertId_checkIfIsAlreadyInserted() {
		
		String[][] shouldWork = {
				{"id_1", "id"},
				{"id_2", "id"}
		};
		for(int i = 0;i<shouldWork.length;i++) {
				assertEquals(shouldWork[i], symbol_table.insertId(shouldWork[i]));
		}
		String[][] shouldNotWork = {
			{"id_1", "id"},
			{"DCL", "word"},
			{"DCL", "id"}
		};
		for(String[] items: shouldNotWork)
			assertEquals(null, symbol_table.insertId(items));
	}
	
	@Test
	@DisplayName("getBySymbol")
	void getBySymbol_symbolReturned() {
		assertEquals("DCL", symbol_table.getBySymbol("DCL"));
		assertEquals(null, symbol_table.getBySymbol("x"));
	}
}
