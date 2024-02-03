package pl1transpiler.SymbolHandeling.Pl1;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PictureMapper {
	
	static HashMap<String, String> picRegex = new HashMap<>();
	
	public PictureMapper() {
		initMap();
	}
	
	public static void initMap() {
		picRegex.put("X", "[A-Za-z]");
		picRegex.put("A", "[A-Za-z ]");
		picRegex.put("9", "[0-9]");
		picRegex.put("(", "{");
		picRegex.put(")", "}");
		picRegex.put("V", ".");
	}
	
	public static String getRegex(String picExpression) {
		return picRegex.get(picExpression);
	}
	
	
	
}


	

