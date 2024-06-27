package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class LoopMapper extends Mapper implements ITranslationBehavior {
	
	private String untilExpression;
	
	
	public String translate(SimpleNode simpleNode) {
		return super.javaWords.WHILE.getValue();
	}
}
