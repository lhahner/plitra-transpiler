package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class LoopMapper extends Mapper implements ITranslationBehavior {
	
	private String untilExpression;
	
	
	public String translate(SimpleNode simpleNode) {
		return Template.WHILE.getValue();
	}
}
