package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserConstants;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class BranchMapper implements ITranslationBehavior{
	
	public String translate(SimpleNode simpleNode) {
		Pl1ParserTreeConstants treeSymbols = null;
		return 
				simpleNode.jjtGetParent().getId() == treeSymbols.JJTBRANCH ? 
				"}" + Template.ELSE.getValue() 
				: Template.IF.getValue();
	}
}
