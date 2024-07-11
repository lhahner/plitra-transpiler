package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * <h1>Summary</h1>
 * The Class EndMapper will be called in the TranlationBehavior Class.
 * During Runtime the Behavior value changes to EndMapper. This happens
 * whenever a END Node occurs in the AST.
 * 
 * This class is for Program-Structure purpose only and will not translate any expression.
 */
public class EndMapper implements ITranslationBehavior{
	
	/** The Constant CLOSINGBRACKET. */
	private static String closingExpression = "\n }";
	
	/**
	 * Gets the closing bracket.
	 *
	 * @return the closing bracket
	 */
	public String getClosingExpression() {
		return closingExpression;
	}
	
	public void setClosingExpression(String closingExpression) {
		this.closingExpression = closingExpression;
	}

	/**
	 * Translate.
	 * Will only add a closing Bracket to the expression,
	 * if an END Node occurs.
	 *
	 * @param simpleNode the END Node
	 * @return the string cotaining the closing bracket.
	 */
	public String translate(SimpleNode simpleNode){
		if(new Mapper().hasChildren(simpleNode)) {
			return null;
		}
		else if(!this.isUntilSibiling(simpleNode)) {
			this.setClosingExpression("}");
		}
		return this.getClosingExpression();
	}
	
	public boolean isUntilSibiling(SimpleNode simpleNode) {
		Pl1ParserTreeConstants treeSymbols = null;
		SimpleNode parent = (SimpleNode)simpleNode.jjtGetParent();
		for(int i = 0; i<parent.jjtGetNumChildren(); i++) {
			if(parent.jjtGetChild(i).getId() == treeSymbols.JJTUNTIL) {
				return true;
			}
		}
		return false;
	}
}
