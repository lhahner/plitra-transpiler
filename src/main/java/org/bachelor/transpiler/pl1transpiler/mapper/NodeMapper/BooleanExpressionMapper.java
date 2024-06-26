package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class BooleanExpressionMapper extends Mapper implements ITranslationBehavior{
	
	public String expression;
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String translate(SimpleNode simpleNode) {
		mapBooleanExpression(simpleNode);
		return "(" + this.getExpression() + ")";
	}
	
	public void mapBooleanExpression(SimpleNode simpleNode) {
		ArrayList<String> expressionList = (ArrayList<String>) simpleNode.jjtGetValue();
		String expression = expressionList.stream().collect(Collectors.joining(" "));
		if(expression.contains("¬")) {
			expression.replaceAll("¬", "!");
		}
		if(expression.contains("&")) {
			expression.replaceAll("&", "&&");
		}
		if(expression.contains("=") && (expression.charAt(expression.indexOf('=')-1) == ' ') ) {
			 expression.replaceFirst(expression.charAt(expression.indexOf('=')) + "", "==");
		}
		this.setExpression(expression);
	}
}
