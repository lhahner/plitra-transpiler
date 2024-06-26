package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserConstants;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class BranchMapper extends Mapper implements ITranslationBehavior{
	
	public String ifStatement;
	public String getIfStatement() {
		return ifStatement;
	}

	public void setIfStatement(String ifStatement) {
		this.ifStatement = ifStatement;
	}

	public String getElseStatement() {
		return elseStatement;
	}

	public void setElseStatement(String elseStatement) {
		this.elseStatement = elseStatement;
	}

	public String elseStatement;
	
	public String translate(SimpleNode simpleNode) {
		this.mapBranchStatement(simpleNode);
		if(this.getElseStatement() != null) {
			return this.getElseStatement();
		}
		return this.getIfStatement();
	}
	
	public void mapBranchStatement(SimpleNode simpleNode) {
		Pl1ParserTreeConstants treeSymbols = null;
		if(simpleNode.jjtGetParent().getId() == treeSymbols.JJTBRANCH) {
			this.setElseStatement("}" + super.javaWords.ELSE.getValue());
		}
		else if(simpleNode.jjtGetParent().getId() == treeSymbols.JJTBODY) {
			this.setIfStatement(super.javaWords.IF.getValue());
		}
	}
}
