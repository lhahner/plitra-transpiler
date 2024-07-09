package org.bachelor.transpiler.pl1transpiler.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * The Class TranslationMapper.
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
	 * Apply translate.
	 *
	 * @param simpleNode the simple node
	 */
	public String applyTranslate(SimpleNode simpleNode) throws MappingException{
		System.out.println(this.translationBehavior.translate(simpleNode));
		return this.translationBehavior.translate(simpleNode);
	}
}



