package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;


/**
 * This class is used to translate an Body Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * The Body will most of the time be mapped as an opening bracket.
 * But if the context of body node is related to an UNTIL-Lopp,
 * it will map two brackets, because of the used do-while expression.
 * For more information check @see {@link #LoopMapper}
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * proc_1: PROC; ... END proc_1; <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * { ... }
 * </code>
 * <br>
 * @author Lennart Hahner
 */
public class BodyMapper implements ITranslationBehavior {

	/** Defines the outline, is default value '{' when there is no content in PL/I-procedure body. */
	private String body = "{";

	/**
	 * Basic getter for String body.
	 * 
	 * @return String body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Basic setter for String body
	 * 
	 * @param value for the body, either { or default.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * This Method is part of the ITranslationBehvior interface. It will be called
	 * whenever the Behavior in class @see TranslationMapper is set to BodyMapper.
	 * Check also @see astMapper for other behavior references.
	 * 
	 * @param simpleNode Node in which the BODY is defined.
	 * @return equivalent Java Program structure.
	 */
	public String translate(SimpleNode simpleNode) throws MappingException{
		if (new Mapper().hasChildren(simpleNode)) {
			if(this.isUntilSibiling(simpleNode)) {
				return "";
			}
			this.setBody("{ \n");
			return this.getBody();
		} else {
			return this.getBody();
		}
	}
	
	/**
	 * This methode specifies the context when the body-node occures.
	 * If the Body-Node occurs within a Until-Node, then
	 * the mapping is slightly different then in a normal context,
	 * because of the extra bracket needed for the do-while loop.
	 * 
	 * @param simpleNode The Body Node
	 * @return True if the Until is a Sibling of the Body Node.
	 */
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
