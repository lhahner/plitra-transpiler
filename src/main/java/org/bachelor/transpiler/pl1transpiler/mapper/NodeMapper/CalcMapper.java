package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
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
 * 2 + 2 <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * 2 + 2 <br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class CalcMapper implements ITranslationBehavior {
	
	/** The terms. */
	private ArrayList<String> terms = new ArrayList<String>();
	
	/**
	 * Gets the terms.
	 *
	 * @return the terms
	 */
	public ArrayList<String> getTerms() {
		return terms;
	}

	/**
	 * Sets the terms.
	 *
	 * @param terms the new terms
	 */
	public void setTerms(ArrayList<String> terms) {
		this.terms = terms;
	}

	/**
	 * Translate.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 * @throws MappingException the mapping exception
	 */
	public String translate(SimpleNode simpleNode) throws MappingException{
		setTerms(simpleNode);
		if(terms != null)
			return terms.stream().collect(Collectors.joining(" "));
		else 
			throw new MappingException("Arithemtic Expression not definied for" + simpleNode.toString() + " in " + this.getClass().toString());
	}
	
	/**
	 * Sets the terms.
	 *
	 * @param simpleNode the new terms
	 */
	public void setTerms(SimpleNode simpleNode) {
		this.terms.add((String) simpleNode.jjtGetValue());
		
		return;
	}
}
