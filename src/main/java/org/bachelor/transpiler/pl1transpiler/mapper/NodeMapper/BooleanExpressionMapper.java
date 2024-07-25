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

/**
 * This class is used to translate an BooleaExpression Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * The Expression is mapped from the PL/I to the Java-Representation.
 * First every counter part is set into a list and afterwards this list
 * will be concatinated
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * (var_1 > 1 & var_1 < 2) <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * (var_1 > 1 && var_1 < 2) <br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class BooleanExpressionMapper implements ITranslationBehavior {

	/** contains the Booleans Java-Expression of the PL/I-Expression */
	private String expression;

	/**
	 * Gets the expression.
	 *
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Sets the expression.
	 *
	 * @param expression the new expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * Behavior this Strategy should implement
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 * @throws MappingException the mapping exception
	 */
	public String translate(SimpleNode simpleNode) throws MappingException {
		Pl1ParserTreeConstants treeSymbols = null;

		ArrayList<String> expressionList = new ArrayList<String>();
		setExpressionList(simpleNode, expressionList);
		mapBooleanExpression(expressionList);

		// TODO Fix quick and dirty here.
		if (simpleNode.jjtGetParent().getId() == treeSymbols.JJTBOOL) {
			return "";
		}
		/** @see Class BodyMapper */
		if (simpleNode.jjtGetParent().getId() == treeSymbols.JJTUNTIL) {
			new EndMapper()
					.setClosingExpression("} \n" + Template.WHILE.getValue() + "(! " + this.getExpression() + ");");
			return "{ \n" + Template.DO.getValue() + "{ \n";

		}

		if (this.getExpression() != null)
			return this.getExpression();
		else
			throw new MappingException("Boolean expression not definied for" + simpleNode.toString() + " in "
					+ this.getClass().toString());
	}

	/**
	 * Map boolean expression from the PL/I Booleanoperators, 
	 * like the negation operator or the and-operator 
	 * to the Java-representation.
	 *
	 * @param expressionList the expression list
	 */
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

	/**
	 * Sets the parameter definition list. This has to be called before calling
	 * mapParamterDefinitionList, since this method will recursively iterate over
	 * all parameter nodes.
	 *
	 * @param paraNode the new parameter definition list
	 * @param expressionList the expression list
	 * @throws MappingException the mapping exception
	 */
	public void setExpressionList(SimpleNode paraNode, ArrayList<String> expressionList) throws MappingException {
		Pl1ParserTreeConstants treeSymbols = null;
		ArrayList<String> expression = (ArrayList<String>) paraNode.jjtGetValue();
		// iterater over ArrayList to parse types
		for (int i = 0; i < expression.size(); i++) {
			if (SymbolTable.getInstance().getBySymbol(expression.get(i)) != null && !expression.get(i).equals("MOD")) {

				expressionList.add(expression.get(i));
				continue;
				
			} else if (expression.get(i).equals("MOD")) {

				BuiltInMapper bm = new BuiltInMapper();
				for (int j = 0; j < paraNode.jjtGetNumChildren(); j++) {
					
					if (paraNode.jjtGetChild(j).getId() == treeSymbols.JJTBUILTIN)
						
						expressionList.add(bm.translate((SimpleNode) paraNode.jjtGetChild(j)));
				}

			} else {
				expressionList.add(expression.get(i));
			}
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
