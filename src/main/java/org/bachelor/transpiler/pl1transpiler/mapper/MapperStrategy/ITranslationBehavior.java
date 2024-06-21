package org.bachelor.transpiler.pl1transpiler.mapper.MapperStrategy;

import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public interface ITranslationBehavior {
	String translate(SimpleNode simpleNode);
}
