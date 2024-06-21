package org.bachelor.transpiler.pl1transpiler.mapper.MapperStrategy;

import java.util.HashMap;

import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class TranslationMapper {
	private ITranslationBehavior translationBehavior;

	void setTranslationBevaior(ITranslationBehavior iTranslationBehavior) {
		// TODO Auto-generated method stub
		this.translationBehavior = iTranslationBehavior;
	}
	
	public void applyTranslate(SimpleNode simpleNode) {
		this.translationBehavior.translate(simpleNode);
	}
}



