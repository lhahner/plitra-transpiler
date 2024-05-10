package org.bachelor.transpiler.pl1transpiler.symboltable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.charset.UnsupportedCharsetException;
import org.junit.jupiter.api.Test;
import org.bachelor.transpiler.pl1transpiler.errorhandling.LexicalErrorException;
import org.bachelor.transpiler.pl1transpiler.symboltable.PictureMapper;
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
	void translateLengthExpression_returnStringForExpressionOfLength(){
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
	void getCharacterRegex_tranlatedCharacterAsRegularExpression() {
		//Testing cases which should work
		char[] shouldWork = { '(',')','A','9', 'V', 'X', 'Z'};
		String[] ExpectedResults = {"{", "}", "[A-Za-z ]", "[0-9]", "[\\.\\*]", "[A-Za-z]", "[0-9 ]"};
		for(int i = 0; i<shouldWork.length;i++) 
			assertEquals(ExpectedResults[i], picture_mapper.getCharacterRegex(shouldWork[i]));
	    
		//Testing cases which should not work
	    char[] shouldNotWork = {'x','a','v'};
	    for(char item : shouldNotWork) 
	    	assertEquals(null, picture_mapper.getCharacterRegex(item));
	}
	
	@Test
	@DisplayName("getRegex")
	void getRegex_translateSingleCharectes() throws UnsupportedCharsetException, LexicalErrorException {
		//Testing cases which should work
		String[] shouldWork = { "(5)","X","A","9", "V", "$", ".", "Z"};
		String[] ExpectedResults = {"{5}", "[A-Za-z]", "[A-Za-z ]", "[0-9]", "[\\.\\*]", "\\$", "[\\.\\*]", "[0-9 ]"};
		
		for(int i = 0; i<shouldWork.length;i++) 
			
			assertEquals(ExpectedResults[i], picture_mapper.getRegex(shouldWork[i]));
			
		//Testing cases which should not work
	    String[] shouldNotWork = {"(A)","XO","0"};
	    assertThrows(UnsupportedCharsetException.class, () -> {
	    	for(String item : shouldNotWork) 
	    		picture_mapper.getRegex(item);
	    });
	}
	
	void getRegex_translateRegexString() throws UnsupportedCharsetException, LexicalErrorException {
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
				"[0-9]{3}",
				"[0-9][0-9][0-9][0-9]",
				"[0-9][0-9][0-9].[0-9][0-9]", 
				"[0-9]{3}[\\.\\*][0-9]{2}", 
				"[A-Za-z ][A-Za-z ][A-Za-z ][0-9][0-9][A-Za-z]", 
				"[0-9][0-9][A-Za-z][0-9][0-9]", 
				"[A-Za-z ][0-9][0-9][A-Za-z][0-9]", 
				"[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]", 
				"[A-Za-z ]{10}", 
				".[0-9][0-9]"
				};
		//Testing 
		for(int i = 0;i<shouldWork.length;i++) 
			assertEquals(ExpectedResults[i], picture_mapper.getRegex(shouldWork[i]));

	}
	
	@Test
	@DisplayName("getRegex Zero token Test")
	void getRegex_zeroTokenInput() throws UnsupportedCharsetException, LexicalErrorException {
	String[] shouldWork = {"ZZZ99", "ZZZZZ", "ZZZV99", "ZZZVZZ"};
	String[] expected = {"[0-9 ][0-9 ][0-9 ][0-9][0-9]", 
						 "[0-9 ][0-9 ][0-9 ][0-9 ][0-9 ]", 
						 "[0-9 ][0-9 ][0-9 ][\\.\\*][0-9][0-9]", 
						 "[0-9 ][0-9 ][0-9 ][\\.\\*][0-9 ][0-9 ]"};	
	//Z can be empty
	for(int i = 0; i<shouldWork.length; i++)
		assertEquals(expected[i], picture_mapper.getRegex(shouldWork[i]));
	}
	
	@Test
	@DisplayName("getRegex * token Test")
	void getRegex_starTokenInput() throws UnsupportedCharsetException, LexicalErrorException {
	String[] shouldWork = {"*****", "***V**"};
	String[] expected = {"[0-9\\*][0-9\\*][0-9\\*][0-9\\*][0-9\\*]",
						 "[0-9\\*][0-9\\*][0-9\\*][\\.\\*][0-9\\*][0-9\\*]"
	};	
	//* can be empty
	for(int i = 0; i<shouldWork.length; i++)
		assertEquals(expected[i], picture_mapper.getRegex(shouldWork[i]));
	}
	
	@Test
	@DisplayName("getRegex $ Format")
	void getRegex_dollarSign() throws UnsupportedCharsetException, LexicalErrorException {
		String[] shouldWork = {
				"$**9.99"
		};
		String[] expected = {
				"\\$[0-9\\*][0-9\\*][0-9][\\.\\*][0-9][0-9]",
				"$**9.99"
		};
		//$ Expression
		for(int i = 0; i<shouldWork.length; i++)
			assertEquals(expected[i], picture_mapper.getRegex(shouldWork[i]));
		}

	@Test
	@DisplayName("Insertion Characters")
	void getRegex_blankIntoBlank() throws UnsupportedCharsetException, LexicalErrorException {
		String shouldWork = "Z,ZZZ,ZZZV99";
		String expected = "[0-9 ][\\,\\*][0-9 ][0-9 ][0-9 ][\\,\\*][0-9 ][0-9 ][0-9 ][\\.\\*][0-9][0-9]";
		assertEquals(expected, picture_mapper.getRegex(shouldWork));
	}
	
	@Test
	@DisplayName("Defining currency symbols")
	void getRegex_currencySymbols() throws UnsupportedCharsetException, LexicalErrorException {
		String shouldWork = "<DM>>>.>>9V99";
		String expected = "DM[ 0-9][ 0-9][ 0-9][\\.\\*][ 0-9][ 0-9][0-9][\\.\\*][0-9][0-9]";
		assertEquals(expected, picture_mapper.getRegex(shouldWork));
	}
	
	@Test
	@DisplayName("Signs and currency symbols")
	void getRegex_signsCurrencySymbols() throws UnsupportedCharsetException, LexicalErrorException {
		String shouldWork = "S999";
		String expected = "[\\+\\- ][0-9][0-9][0-9]";
		assertEquals(expected, picture_mapper.getRegex(shouldWork));
	}
	
	@Test
	@DisplayName("Credit, debit, overpunched, and zero replacement characters")
	void getRegex_creditDebitOverpunched() throws UnsupportedCharsetException, LexicalErrorException {
		String[] shouldWork = {"$Z.99CR", "$ZZV99DB"};
		String[] expected = {"\\$[0-9 ][\\.\\*][0-9][0-9]CR", "\\$[0-9 ][0-9 ][\\.\\*][0-9][0-9]DB "};
		for(int i = 0; i<shouldWork.length;i++)
			assertEquals(expected[i], picture_mapper.getRegex(shouldWork[i]));
	}
	
	@Test
	@DisplayName("Exponentcharacters")
	void getRegex_ExponentCharacter() throws UnsupportedCharsetException, LexicalErrorException {
		String[] shouldWork = {"V999ES99", "ZZZV99KS99"};
		String[] expected = {"[\\.\\*][0-9][0-9][0-9]E[\\+\\- ][0-9][0-9]", "[0-9 ][0-9 ][0-9 ][\\.\\*][0-9][0-9][\\+\\- ][0-9][0-9]"};
		for(int i = 0; i<shouldWork.length;i++)
			assertEquals(expected[i], picture_mapper.getRegex(shouldWork[i]));
	}
	
	
}



