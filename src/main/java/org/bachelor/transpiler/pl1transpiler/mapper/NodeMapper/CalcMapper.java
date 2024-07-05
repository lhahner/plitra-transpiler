package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class CalcMapper implements ITranslationBehavior {
	
	private ArrayList<String> terms = new ArrayList<String>();
	
	public String translate(SimpleNode simpleNode) {
		setTerms(simpleNode);
		return terms.stream().collect(Collectors.joining(" "));
	}
	
	public void setTerms(SimpleNode simpleNode) {
		this.terms.add((String) simpleNode.jjtGetValue());
		if (new Mapper().hasChildren(simpleNode)) {
			setTerms((SimpleNode) simpleNode.jjtGetChild(0));
		}
		return;
	}
}
