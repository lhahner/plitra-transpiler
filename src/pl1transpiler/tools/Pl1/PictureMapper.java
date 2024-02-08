package pl1transpiler.tools.Pl1;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
		picRegex.put('9', "[0-9]");
		picRegex.put('(', "{");
		picRegex.put(')', "}");
		picRegex.put('V', "\\.");
	}

	/**
	 * @param regex the PL/I Length Expression that should be parsed. Ex.: (4)A
	 * @return the translated regular Expression.
	 */
	public String translateLengthExpression(String regex) {
		String length = regex.substring(1, regex.indexOf(')'));
		String exp = regex.substring(regex.indexOf(')') + 1, regex.length());
		String regExpression = "";
		for (int i = 0; i < exp.length(); i++) {
			switch (exp.charAt(i)) {
			case 'A':
				regExpression = regExpression + this.getRegex('A');
				break;
			case '9':
				regExpression = regExpression + this.getRegex('9');
				break;
			case 'X':
				regExpression = regExpression + this.getRegex('X');
				break;
			case 'V':
				regExpression = regExpression + this.getRegex('V');
				break;
			default:
				System.out.println("Error in translateExpression");
			}
		}
		return regExpression + "{" + length + "}";
	}

	/**
	 * @param picExpression the Character that should be translated to a regular
	 *                      Expression.
	 * @return the equivalent regular Expression from the HashMap.
	 */
	public static String getRegex(char picExpression) {
		return picRegex.get(picExpression);
	}
	
	/**
	 * @param regex the PL/I expression.
	 * @return the regex.
	 */
	public String getRegex(String regex) {
		
		String regExpression = "";
		if(regex.contains("(")) {
			regExpression = this.translateLengthExpression(regex);
		}
		else {
			for(int i = 0; i<regex.length();i++) {	
				regExpression = regExpression + this.getRegex(regex.charAt(i));
			}
		}
		return regExpression;
	}

}
