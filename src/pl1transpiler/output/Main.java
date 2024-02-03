package pl1transpiler.output; 
import java.math.BigDecimal; import java.util.HashMap; 
 import java.util.HashMap; 
 import java.util.regex.Matcher; 
 import java.util.regex.Pattern;import pl1transpiler.SymbolHandeling.Pl1.PictureMapper; 
public class Main { PICTURE  MSG99_A = new  PICTURE  ("XXXX");
public static void main(String[] args) { };  }
interface Datatype { 
public void init(String content); 
}; 
 
class PICTURE implements Datatype{ 
Pattern pattern; 
String content; 
public PICTURE(String regex) { 
pattern = Pattern.compile(regex); } 
public void init(String content) { 
Matcher matcher = pattern.matcher(content); 
if(matcher.matches()) { 
this.content = content; } 
else return; } }
 
class PL1_CHAR implements Datatype{ char[] varname; public PL1_CHAR(int limit) { 
varname =  new char[limit]; } 
@Override public String toString() { 
 String string = new String(varname); 
return string; } 
public void init(String varname) { 
for(int i = 0; i<this.varname.length; i++) { 
this.varname[i] = varname.charAt(i); }}} 
 
