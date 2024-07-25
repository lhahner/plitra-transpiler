package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

/**
 * This class is used to translate an BooleaExpression Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * WRITE FILE(file_1) FROM(var_1);<br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * BufferedWriter writer = new BufferedWriter(file_1);<br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class WriteMapper implements ITranslationBehavior {

	/**
	 * Translate.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 * @throws MappingException the mapping exception
	 */
	public String translate(SimpleNode simpleNode) throws MappingException {
		
		if (simpleNode.jjtGetValue() instanceof String) {
			
			return Template.BUFFEREDWRITER.getValue() + " writer" + " = " + Template.NEW.getValue() + " " + Template.BUFFEREDWRITER.getValue() + "("
					+ Template.NEW.getValue() + " " + Template.FILEWRITER.getValue() + "(" + (String) simpleNode.jjtGetValue() + ")); \n";
			
		} else if (simpleNode.jjtGetValue() instanceof String[]) {
			
			String[] values = (String[]) simpleNode.jjtGetValue();
			
			return Template.BUFFEREDWRITER.getValue() + " writer" + " = " + Template.NEW.getValue() + " " + Template.BUFFEREDWRITER.getValue() + "("
					+ Template.NEW.getValue() + " " + Template.FILEWRITER.getValue() + "(" + values[0] + ")); \n " + "writer." + Template.WRITE.getValue()
					+ "(" + values[1] + "); \n";
		}
		throw new MappingException("Error while Mapping " + simpleNode.toString());
	}
}
