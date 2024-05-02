package org.bachelor.transpiler.pl1transpiler.tools.pli;

import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.AttributeNotFoundException;


public class PictureMapper {

	static HashMap<Character, String> picRegex = new HashMap<>();

	public PictureMapper() {
		initMap();
	}

	/**
	 * initalizes the picRegex Hashmap.
	 */
	public static void initMap() {
		picRegex.put('X', "[A-Za-z]");
		picRegex.put('A', "[A-Za-z ]");
		picRegex.put('9', "[0-9 ]");
		picRegex.put('(', "{");
		picRegex.put(')', "}");
		picRegex.put('V', "\\.");
	}

	/**
	 * @param regex the PL/I Length Expression that should be parsed. Ex.: (4)A
	 * @return the translated regular Expression.
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
	 * @param picExpression the Character that should be translated to a regular
	 *                      Expression.
	 * @return the equivalent regular Expression from the HashMap.
	 */
	public static String getCharacterRegex(char picExpression){
		return picRegex.get(picExpression);
	}
	
	/**
	 * @param The PL/I Picture limitation.
	 * @return the equivalent Regex.
	 */
	public String getRegex(String regex) throws UnsupportedCharsetException{
		
		if(picRegex.isEmpty()) {
			initMap();
		}
		
		String regExpression = "";
		if(regex.contains("(")) {
			regExpression = this.translateLengthExpression(regex);
		}
		else {
			for(int i = 0; i<regex.length();i++) {	
				regExpression = regExpression + this.getCharacterRegex(regex.charAt(i));
			}
		}
		return regExpression;
	}

}
