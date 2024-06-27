package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class ReadFileMapper extends Mapper implements ITranslationBehavior {
	
	public String fileIdentifier;
	
	public String getFileIdentifier() {
		return fileIdentifier;
	}

	public void setFileIdentifier(String fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}

	public String translate(SimpleNode simpleNode) {
		this.setFileIdentifier((String)simpleNode.jjtGetValue());
		
		return simpleNode.jjtGetValue() != null ? 
				super.javaWords.SCANNER.getValue() + "(" + this.getFileIdentifier() + ")"
			  : ""	;
	}
}
