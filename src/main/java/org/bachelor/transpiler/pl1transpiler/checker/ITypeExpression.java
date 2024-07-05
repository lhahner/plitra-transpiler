package org.bachelor.transpiler.pl1transpiler.checker;

/**
 * The Interface ITypeExpression,
 * this should be implemented by every leaf object,
 * which should be equivalent to a type of the parse tree.
 */
public interface ITypeExpression {
	
	/**
	 * Gets the type for the used
	 * Type to check.
	 *
	 * @return the type
	 */
	public void getType();
}
