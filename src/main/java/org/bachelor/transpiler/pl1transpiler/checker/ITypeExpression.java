package org.bachelor.transpiler.pl1transpiler.checker;

/**
 * This Interface should be implemented by
 * every leaf of the Composite.
 * It is used to make an consistent checking
 * of the types which should be checked in 
 * this type checking module. 
 * If the developer wants to implement any other 
 * checking, for example checking for the required
 * length of the assigend value, a method should
 * be implemented here first.
 * 
 * @author Lennart Hahner
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
