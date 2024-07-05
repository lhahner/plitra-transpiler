package org.bachelor.transpiler.pl1transpiler.mapper;

import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

/**
 * The Interface ITranslationBehavior.
 */
public interface ITranslationBehavior {
	
	/**
	 *  Will translate a Parsetree Node into a Java Expression.
	 *
	 * @param simpleNode the simple node which should be translated
	 * @return the string which is the java representation
	 */
	String translate(SimpleNode simpleNode);
}
