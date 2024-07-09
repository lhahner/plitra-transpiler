package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class WriteMapper implements ITranslationBehavior {

	public String translate(SimpleNode simpleNode) throws MappingException {
		
		if (simpleNode.jjtGetValue() instanceof String) {
			
			return Template.BUFFEREDWRITER.getValue() + " writer" + " = " + Template.NEW.getValue() + " " + Template.BUFFEREDWRITER.getValue() + "("
					+ Template.NEW.getValue() + " " + Template.FILEWRITER.getValue() + "(" + (String) simpleNode.jjtGetValue() + "));";
			
		} else if (simpleNode.jjtGetValue() instanceof String[]) {
			
			String[] values = (String[]) simpleNode.jjtGetValue();
			
			return Template.BUFFEREDWRITER.getValue() + " writer" + " = " + Template.NEW.getValue() + " " + Template.BUFFEREDWRITER.getValue() + "("
					+ Template.NEW.getValue() + " " + Template.FILEWRITER.getValue() + "(" + values[0] + ")); \n " + "writer." + Template.WRITE.getValue()
					+ "(" + values[1] + ");";
		}
		throw new MappingException("Error while Mapping " + simpleNode.toString());
	}
}
