package org.bachelor.transpiler.pl1transpiler.mapper;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

/**
 * This Interface should be implemented
 * by every Strategy used to translate 
 * a PL/I-Expression. All the specified
 * Methods are representing the transformation
 * behavior of the class that is implementing 
 * this Interface.
 * 
 * @author Lennart Hahner
 */
public interface ITranslationBehavior {
	
	/**
	 *  Will translate a Parsetree Node into a Java Expression.
	 *
	 * @param simpleNode the simple node which should be translated
	 * @return the string which is the java representation
	 */
	String translate(SimpleNode simpleNode) throws MappingException;
}
