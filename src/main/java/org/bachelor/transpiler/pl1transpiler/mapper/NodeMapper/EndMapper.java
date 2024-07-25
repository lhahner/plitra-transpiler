package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * This class is used to translate an BooleaExpression Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * END; <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * } <br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
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
			this.setClosingExpression("} \n");
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
