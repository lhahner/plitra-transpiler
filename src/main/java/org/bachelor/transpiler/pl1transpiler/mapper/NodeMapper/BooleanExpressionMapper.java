package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.Region;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class BooleanExpressionMapper extends Mapper implements ITranslationBehavior {

	private String expression;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String translate(SimpleNode simpleNode) {
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
			return "{\n"
				+	Template.IF.getInstance()
				+  this.getExpression()
				+  "{" + Template.BREAK.getInstance() + ";" + " }";
		}
		return this.getExpression();
	}

	public void mapBooleanExpression(ArrayList<String> expressionList) {

		String expression = expressionList.stream().collect(Collectors.joining(" "));
		if (expression.contains("�")) {
			expression = expression.replaceAll("�", "!");
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
			if (symbols.getBySymbol(expression.get(i)) != null) {
				expressionList.add(expression.get(i).concat("." + Template.TONUMERIC.getInstance() + "()"));
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
