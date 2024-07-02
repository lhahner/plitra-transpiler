package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
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
	private final static String CLOSINGBRACKET = "\n }";
	
	/**
	 * Gets the closing bracket.
	 *
	 * @return the closing bracket
	 */
	public String getClosingBracket() {
		return CLOSINGBRACKET;
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
		return this.getClosingBracket();
	}
}
