package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserConstants;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

/**
 * This class is used to translate an BooleaExpression Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * IF <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * if <br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class BranchMapper implements ITranslationBehavior{
	
	/**
	 * Behavior this Strategy should implement.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 * @throws MappingException the mapping exception
	 */
	public String translate(SimpleNode simpleNode) throws MappingException{
		Pl1ParserTreeConstants treeSymbols = null;
		return 
				simpleNode.jjtGetParent().getId() == treeSymbols.JJTBRANCH ? 
				"\n } " + Template.ELSE.getValue() 
				: Template.IF.getValue();
	}
}
