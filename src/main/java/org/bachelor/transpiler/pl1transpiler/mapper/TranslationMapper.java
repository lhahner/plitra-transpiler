package org.bachelor.transpiler.pl1transpiler.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class TranslationMapper {
	private ITranslationBehavior translationBehavior;
	private ArrayList<String> javaExpression = new ArrayList<String>();
	
	void setTranslationBevaior(ITranslationBehavior iTranslationBehavior) {
		// TODO Auto-generated method stub
		this.translationBehavior = iTranslationBehavior;
	}
	
	public void applyTranslate(SimpleNode simpleNode) {
		System.out.println(this.translationBehavior.translate(simpleNode));
		javaExpression.add(this.translationBehavior.translate(simpleNode));
	}
}



