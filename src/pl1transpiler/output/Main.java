package pl1transpiler.output; 
import java.math.BigDecimal; import java.util.HashMap; 
 import java.util.HashMap; 
 import java.util.regex.Matcher; 
 import java.util.regex.Pattern; 
public class Main { 
public static void main(String[] args) { }; PICTURE  MSG99_3 = new  PICTURE  ("[A-Za-z ]{4}");
} 
interface Datatype { 
public void init(String content); 
}; 
 
class PICTURE {
	Pattern pattern;
	String content;

	public PICTURE(String regex) {
		pattern = Pattern.compile(regex);
	}

	public void init(String content) {
		Matcher matcher = pattern.matcher(content);
		if (matcher.matches()) {
			this.content = content;
		}
	}
} 
class PL1_CHAR implements Datatype{ char[] varname; public PL1_CHAR(int limit) { 
varname =  new char[limit]; } 
@Override public String toString() { 
 String string = new String(varname); 
return string; } 
public void init(String varname) { 
for(int i = 0; i<this.varname.length; i++) { 
this.varname[i] = varname.charAt(i); }}} 
 
class BINARY implements Datatype {
	byte[] content;
	
	public BINARY(int limit) {
		content = new byte[limit];
	}
	
	public void init(String content) {
		for(int i = 0; i<this.content.length;i++) {
			this.content[i] = (byte)content.charAt(i);
		}
	}
} 
