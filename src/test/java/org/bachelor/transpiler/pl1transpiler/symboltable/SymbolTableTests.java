package org.bachelor.transpiler.pl1transpiler.symboltable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;

import javax.management.AttributeNotFoundException;

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
				{"id_1", "id", "1"},
				{"id_2", "id", "2"}
		};
		for(int i = 0;i<shouldWork.length;i++) {
				assertEquals(shouldWork[i], symbol_table.insertId(shouldWork[i]));
		}
		String[][] shouldNotWork = {
			{"id_1", "id", "1"},
			{"DCL", "word", "2"},
			{"DCL", "id", "1"}
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
	
	@Test
	@DisplayName("getByType")
	void getByType_typeReturned() {
		String[] arr = {"var_1", "1", "1", "id"};
		symbol_table.insertId(arr);
		
		ArrayList<String []> expected = new ArrayList<String []>();
		expected.add(arr);
		
		assertEquals(expected.get(0), symbol_table.getByType("id").get(0));
		
	}
	
}
