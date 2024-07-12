package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class CalcMapper implements ITranslationBehavior {
	
	private ArrayList<String> terms = new ArrayList<String>();
	
	public ArrayList<String> getTerms() {
		return terms;
	}

	public void setTerms(ArrayList<String> terms) {
		this.terms = terms;
	}

	public String translate(SimpleNode simpleNode) throws MappingException{
		setTerms(simpleNode);
		if(terms != null)
			return terms.stream().collect(Collectors.joining(" "));
		else 
			throw new MappingException("Arithemtic Expression not definied for" + simpleNode.toString() + " in " + this.getClass().toString());
	}
	
	public void setTerms(SimpleNode simpleNode) {
		this.terms.add((String) simpleNode.jjtGetValue());
		if (new Mapper().hasChildren(simpleNode)) {
			setTerms((SimpleNode) simpleNode.jjtGetChild(0));
		}
		return;
	}
}
