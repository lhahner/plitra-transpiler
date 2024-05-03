package org.bachelor.transpiler.pl1transpiler.tools.pli;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.charset.UnsupportedCharsetException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

/**
 * @todo pl1_language_ref, p. 334-344 testing of all other formats
 * @author lennart.hahner
 *
 */
public class PictureMapperTests {
	
    PictureMapper picture_mapper;
	@BeforeEach
	void init() {
		picture_mapper = new PictureMapper();
	}
	
	@Test
	@DisplayName("translateLengthExpression")
	void translateLengthExpression_returnStringForExpressionOfLength_StringAsRegex(){
		//Testing Cases which should work
		String[] shouldWork = { "(5)","(10)","(100)","(1000)", "(10000)"};
		String[] ExpectedResults = {"{5}", "{10}", "{100}", "{1000}", "{10000}"};
		
		for(int i = 0; i<shouldWork.length;i++) 
			assertEquals(ExpectedResults[i], picture_mapper.translateLengthExpression(shouldWork[i]));
	    
		//Testing Cases which should not work
		String[] shouldNotWork = {"(0)","(A)","A", "1", "10"};
	    assertThrows(UnsupportedCharsetException.class, () -> {
	    	for(String item : shouldNotWork) 
	    		picture_mapper.translateLengthExpression(item);
	    });
	}
	
	@Test
	@DisplayName("getCharacterRegex")
	void getCharacterRegex_tranlatedCharacterAsRegularExpression_regexCharacterString() {
		//Testing cases which should work
		char[] shouldWork = { '(',')','A','9', 'V', 'X'};
		String[] ExpectedResults = {"{", "}", "[A-Za-z ]", "[0-9 ]", "\\.", "[A-Za-z]"};
	
		for(int i = 0; i<shouldWork.length;i++) 
			assertEquals(ExpectedResults[i], picture_mapper.getCharacterRegex(shouldWork[i]));
	    
		//Testing cases which should not work
	    char[] shouldNotWork = {'x','a','v'};
	    for(char item : shouldNotWork) 
	    	assertEquals(null, picture_mapper.getCharacterRegex(item));
	}
	
	@Test
	@DisplayName("getRegex")
	void getRegex_translateSingleCharectes_regexString() {
		//Testing cases which should work
		String[] shouldWork = { "(5)","X","A","9", "V"};
		String[] ExpectedResults = {"{5}", "[A-Za-z]", "[A-Za-z ]", "[0-9 ]", "\\."};
		
		for(int i = 0; i<shouldWork.length;i++) 
			assertEquals(ExpectedResults[i], picture_mapper.getRegex(shouldWork[i]));
	    
		//Testing cases which should not work
	    String[] shouldNotWork = {"(A)","XO","0"};
	    assertThrows(UnsupportedCharsetException.class, () -> {
	    	for(String item : shouldNotWork) 
	    		picture_mapper.getRegex(item);
	    });
	}
	
	void getRegex_translateRegexString_regexString() {
		String[] shouldWork = { 
				"(3)9",
				"9999",
				"999V99",
				"(3)9V(2)9", 
				"AAA99X", 
				"99X99", 
				"A99X9", 
				"XXXXXX", 
				"(10)A", 
				"V99"
				};
		String[] ExpectedResults = { 
				"[0-9 ]{3}",
				"[0-9 ][0-9 ][0-9 ][0-9 ]",
				"[0-9 ][0-9 ][0-9].[0-9 ][0-9 ]", 
				"[0-9 ]{3}\\.[0-9]{2}", 
				"[A-Za-z ][A-Za-z ][A-Za-z ][0-9 ][0-9 ][A-Za-z]", 
				"[0-9 ][0-9 ][A-Za-z][0-9 ][0-9 ]", 
				"[A-Za-z ][0-9][0-9 ][A-Za-z][0-9 ]", 
				"[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]", 
				"[A-Za-z ]{10}", 
				".[0-9 ][0-9 ]"
				};
		
		for(int i = 0;i<shouldWork.length;i++) 
			assertEquals(ExpectedResults[i], shouldWork[i]);

	}
	}
