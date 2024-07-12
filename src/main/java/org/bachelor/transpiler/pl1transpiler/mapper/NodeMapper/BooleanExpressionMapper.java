package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.Region;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class BooleanExpressionMapper implements ITranslationBehavior {

	private String expression;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String translate(SimpleNode simpleNode) throws MappingException {
		Pl1ParserTreeConstants treeSymbols = null;

		ArrayList<String> expressionList = new ArrayList<String>();
		setExpressionList(simpleNode, expressionList);
		mapBooleanExpression(expressionList);
		
		// TODO Fix quick and dirty here.
		if (simpleNode.jjtGetParent().getId() == treeSymbols.JJTBOOL) {
			return "";
		}
		/**@see Class BodyMapper */
		if (simpleNode.jjtGetParent().getId() == treeSymbols.JJTUNTIL) {
			new EndMapper().setClosingExpression("} " + Template.WHILE.getValue() + "(! " + this.getExpression() + ");");
			return "{" +  Template.DO.getValue() + "{";
				
		}
		
		if(this.getExpression() != null)
			return this.getExpression();
		else 
			throw new MappingException("Boolean expression not definied for" + simpleNode.toString() + " in " + this.getClass().toString());
	}

	public void mapBooleanExpression(ArrayList<String> expressionList) {

		String expression = expressionList.stream().collect(Collectors.joining(" "));
		if (expression.contains("¬")) {
			expression = expression.replaceAll("¬", "!");
		}
		if (expression.contains("&")) {
			expression = expression.replaceAll("&", "&&");
		}
		if (Pattern.matches(".* = .*", expression)) {
			expression = expression.replaceAll(" = ", " == ");
		}
		this.setExpression(expression);
	}

	public void mapIndentifier() {

	}

	/**
	 * Sets the parameter definition list. This has to be called before calling
	 * mapParamterDefinitionList, since this method will recursively iterate over
	 * all parameter nodes.
	 * 
	 * @param paraNode the new parameter definition list
	 */
	public void setExpressionList(SimpleNode paraNode, ArrayList<String> expressionList) {
		Pl1ParserTreeConstants treeSymbols = null;
		ArrayList<String> expression = (ArrayList<String>) paraNode.jjtGetValue();
		// iterater over ArrayList to parse types
		for (int i = 0; i < expression.size(); i++) {
			if (SymbolTable.getInstance().getBySymbol(expression.get(i)) != null) {
				expressionList.add(expression.get(i));
				continue;
			}
			expressionList.add(expression.get(i));
		}
		setExpression(expressionList.stream().collect(Collectors.joining(" ")));
		// Iterate over childs
		for (int i = 0; i < paraNode.jjtGetNumChildren(); i++) {
			if (paraNode.jjtGetChild(i).getId() == treeSymbols.JJTBOOL) {
				setExpressionList((SimpleNode) paraNode.jjtGetChild(i), expressionList);
			}
		}
		return;
	}
}
