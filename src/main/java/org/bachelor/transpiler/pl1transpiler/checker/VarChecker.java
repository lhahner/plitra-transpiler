package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;
import java.util.List;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;

public class VarChecker implements ITypeExpression {
	
	private String identifier;
	
	private List<ITypeExpression> typeExpressions;
	
	public VarChecker() {
		this.typeExpressions = new ArrayList<>();
	} 
	
	public void getType() {
		typeExpressions.forEach(ITypeExpression::getType);
	}
	
	public void addTypeExpression(ITypeExpression typeExpression) {
		typeExpressions.add(typeExpression);
	}
	
	public void removeTypeExpression(ITypeExpression typeExpression) {
		typeExpressions.add(typeExpression);
	}
	
	
}
