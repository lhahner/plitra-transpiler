package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.AttributeNotFoundException;

import org.bachelor.transpiler.pl1transpiler.errorhandling.LexicalErrorException;


// TODO: Auto-generated Javadoc
/**
 * The Class PictureMapper.
 */
public class PictureMapper {

	/** The pic regex. */
	static HashMap<Character, String> picRegex = new HashMap<>();

	/**
	 * Instantiates a new picture mapper.
	 */
	public PictureMapper() {
		initMap();
	}

	/**
	 * initalizes the picRegex Hashmap.
	 */
	public static void initMap() {
		picRegex.put('X', "[A-Za-z]");
		picRegex.put('A', "[A-Za-z ]");
		picRegex.put('9', "[0-9]");
		picRegex.put('(', "{");
		picRegex.put(')', "}");
		picRegex.put('V', "[\\.\\*]");
		picRegex.put('.', "[\\.\\*]");
		picRegex.put('Z', "[0-9 ]");
		picRegex.put('*', "[0-9\\*]");
		picRegex.put('$', "\\$");
		picRegex.put('B', " ");
		picRegex.put(',', "[\\,\\*]");
		picRegex.put('/', "[\\/\\*]");
		picRegex.put('<', "");
		picRegex.put('>', "[ 0-9]");
		picRegex.put('D', "D");
		picRegex.put('M', "M");
		picRegex.put('K', "K");
		picRegex.put('€', "€");
		picRegex.put('S', "[\\+\\- ]");
		picRegex.put('+', "\\+");
		picRegex.put('-', "\\-");
		picRegex.put('R', "R");
		picRegex.put('C', "C");
		picRegex.put('E', "E");
		picRegex.put('K', "");
	}

	/**
	 * Translate length expression.
	 *
	 * @param regex the PL/I Length Expression that should be parsed. Ex.: (4)A
	 * @return the translated regular Expression.
	 * @throws UnsupportedCharsetException the unsupported charset exception
	 */
	public String translateLengthExpression(String regex) throws UnsupportedCharsetException{
		if(Character.isDigit(regex.charAt(regex.indexOf('(') + 1))) {

		String length = regex.substring(1, regex.indexOf(')'));
		String exp = regex.substring(regex.indexOf(')') + 1, regex.length());
		String regExpression = "";
		for (int i = 0; i < exp.length(); i++) {
			switch (exp.charAt(i)) {
			case 'A':
				regExpression = regExpression + this.getCharacterRegex('A');
				break;
			case '9':
				regExpression = regExpression + this.getCharacterRegex('9');
				break;
			case 'X':
				regExpression = regExpression + this.getCharacterRegex('X');
				break;
			case 'V':
				regExpression = regExpression + this.getCharacterRegex('V');
				break;
			default:
				System.out.println("Error in translateExpression");
			}
		}
		return regExpression + "{" + length + "}";
	 }
		else {
			throw new UnsupportedCharsetException("Alphnumeric Character not allowed for lenght limitations");
		}
		
	}

	/**
	 * Gets the character regex.
	 *
	 * @param picExpression the Character that should be translated to a regular
	 *                      Expression.
	 * @return the equivalent regular Expression from the HashMap.
	 */
	public static String getCharacterRegex(char picExpression){
		return picRegex.get(picExpression);
	}
	
	/**
	 * Gets the regex.
	 *
	 * @param regex the regex
	 * @return the equivalent Regex.
	 * @throws UnsupportedCharsetException the unsupported charset exception
	 * @throws LexicalErrorException the lexical error exception
	 */
	public String getRegex(String regex) throws UnsupportedCharsetException, LexicalErrorException{
		int decimalPointCounter = 0;
		if(picRegex.isEmpty()) {
			initMap();
		}
		
		String regExpression = "";
		if(regex.contains("(")) {
			regExpression = this.translateLengthExpression(regex);
		}
		
		else {
			for(int i = 0; i<regex.length();i++) {
				if(regex.charAt(i) == 'V' || regex.charAt(i) == '.') {
					if(decimalPointCounter > 1) {
						throw new LexicalErrorException("Decimalpoint is only allowed once. For Example DCL X PIC(9VV9) is not allowed.");
					}
					else {
					decimalPointCounter++;
					}
				}
				else if(regex.charAt(i) == 'D' && regex.charAt(i+1) == 'B') {
					regExpression = regExpression + this.getDebitKey();
					continue;
				}
				regExpression = regExpression + this.getCharacterRegex(regex.charAt(i));
			}
		}
		return regExpression;
	}
	
	/**
	 * This Method should translate the occured DEBTI Type,
	 * since B is also used for BLANKs.
	 *
	 * @return Just the String DB
	 */
	public String getDebitKey() {
		return "DB";
	}

}
