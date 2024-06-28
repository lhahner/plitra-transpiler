package org.bachelor.transpiler.pl1transpiler.mapper;

import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

public interface ITranslationBehavior {
	/** The symbols. */
	String translate(SimpleNode simpleNode);
}
