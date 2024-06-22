package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class EndMapper extends Mapper implements ITranslationBehavior{
	
	private final static String CLOSINGBRACKET = "\n }";
	
	public String getClosingBracket() {
		return CLOSINGBRACKET;
	}

	public String translate(SimpleNode simpleNode){
		if(super.hasChildren(simpleNode)) {
			return null;
		}
		return this.getClosingBracket();
	}
}
