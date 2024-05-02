package org.bachelor.transpiler.pl1transpiler.output; 
import java.math.BigDecimal; import java.util.HashMap; 
 import java.util.HashMap; 
 import java.util.regex.Matcher; 
 import java.util.regex.Pattern; 
public class Main { 
public static void main(String[] args) { }; private  PL1_CHAR MSG01 = new  PL1_CHAR(20); 
private  PL1_CHAR MSG02 = new  PL1_CHAR(20); 
private  PL1_CHAR MSG03 = new  PL1_CHAR(20); 
private  PL1_CHAR MSG04 = new  PL1_CHAR(15); 
private  PL1_CHAR MSG05 = new  PL1_CHAR(16); 
private  PL1_CHAR MSG06 = new  PL1_CHAR(16); 
private  PL1_CHAR MSG07 = new  PL1_CHAR(20); 
private  PL1_CHAR MSG08 = new  PL1_CHAR(20); 
private  PL1_CHAR MSG09 = new  PL1_CHAR(20); 
private  PL1_CHAR MSG10 = new  PL1_CHAR(20); 
private  PL1_CHAR MSG11 = new  PL1_CHAR(8); 
private  PL1_CHAR MSG12 = new  PL1_CHAR(22); 
private  PL1_CHAR MSG20 = new  PL1_CHAR(19); 
private  PL1_CHAR MSG21 = new  PL1_CHAR(23); 
private  PL1_CHAR MSG22 = new  PL1_CHAR(21); 
private  PL1_CHAR MSG23 = new  PL1_CHAR(22); 
private  PL1_CHAR MSG24 = new  PL1_CHAR(21); 
private  PL1_CHAR MSG25 = new  PL1_CHAR(16); 
private  PL1_CHAR MSG26 = new  PL1_CHAR(17); 
private  PL1_CHAR MSG27 = new  PL1_CHAR(20); 
private  PL1_CHAR MSG28 = new  PL1_CHAR(20); 
private  PL1_CHAR MSG29 = new  PL1_CHAR(19); 
private  PL1_CHAR MSG30 = new  PL1_CHAR(18); 
private  PL1_CHAR MSG31 = new  PL1_CHAR(19); 
private  PL1_CHAR MSG97 = new  PL1_CHAR(24); 
private  PL1_CHAR MSG98 = new  PL1_CHAR(19); 
BigDecimal  DEST_PORT = new  BigDecimal ("2147483647,0");BigDecimal  SOCKDESC = new  BigDecimal ("2147483647,0");BigDecimal  NIFLAGS = new  BigDecimal ("2147483647,0");BigDecimal  SNAPLEN = new  BigDecimal ("2147483647,0");BigDecimal  WK1 = new  BigDecimal ("2147483647,0");BigDecimal  WK2 = new  BigDecimal ("2147483647,0");BigDecimal  WK3 = new  BigDecimal ("2147483647,0");BigDecimal  RETLEN = new  BigDecimal ("2147483647,0");BigDecimal  WK_LENGTH = new  BigDecimal ("2147483647,0");BINARY  WK_SUBSCRIPT = new  BINARY  (1);
MSG99 MSG99_ = new  MSG99();} 
class  MSG99 { 
 private  PL1_CHAR MSG99_1 = new  PL1_CHAR(29); 
private  PL1_CHAR MSG99_2 = new  PL1_CHAR(15); 
PICTURE  MSG99_3 = new  PICTURE  ("[A-Za-z ]{4}");
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
