package org.bachelor.transpiler.pl1transpiler.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * This class is the context-class
 * of the strategy Design-Pattern. 
 * It is used to set the strategy in a certain context.
 * The context is specified by the caller.
 * After the behavior has changed @see #applyTranslate(SimpleNode)
 * needs to be called to execute a certain translation (behavior).
 * 
 * @author Lennart Hahner
 */
public class TranslationMapper {
	
	/** The translation behavior. */
	private ITranslationBehavior translationBehavior;
	
	/**
	 * Sets the translation bevaior.
	 *
	 * @param iTranslationBehavior the new translation bevaior
	 */
	void setTranslationBevaior(ITranslationBehavior iTranslationBehavior) {
		this.translationBehavior = iTranslationBehavior;
	}
	
	/**
	 * Applies the specified Strategy by @see {@link #translationBehavior}.
	 * This execution will also print the Java-String resulting from 
	 * the translation method.
	 *
	 * @param simpleNode the node which the strategy should be applied on.
	 */
	public String applyTranslate(SimpleNode simpleNode) throws MappingException{
		System.out.println(this.translationBehavior.translate(simpleNode));
		return this.translationBehavior.translate(simpleNode);
	}
}



