package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;
import java.util.List;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;

/**
 * The Class VarChecker is the Composite of the Composite 
 * Design Pattern. It has all the declared types in a list and
 * invokes the used getType() method to check all the assigned 
 * values.
 */
public class VarChecker implements ITypeExpression {
	
	/** The type expressions containing all Type Objects */
	private List<ITypeExpression> typeExpressions;
	
	/** Instantiates a new VarChecker and instantiates an ArrayList.  */
	public VarChecker() {
		this.typeExpressions = new ArrayList<>();
	} 
	
	/**
	 * This invokes all getType methods from all
	 * datatype classes like String- or DecimalChecker.
	 */
	public void getType() {
		typeExpressions.forEach(ITypeExpression::getType);
	}
	
	/**
	 * Adds the type expression Class like DecimalChecker
	 * or StringChecker to the List containing all type
	 * declaration with the assignment.
	 *
	 * @param typeExpression the type expression
	 */
	public void addTypeExpression(ITypeExpression typeExpression) {
		typeExpressions.add(typeExpression);
	}
	
	/**
	 * Removes the type expression Class like DecimalChecker
	 * or StringChecker from the @see {@link #typeExpressions} List.
	 * 
	 * @param typeExpression Class that implements TypeExpression interface
	 */
	public void removeTypeExpression(ITypeExpression typeExpression) {
		typeExpressions.remove(typeExpression);
	}
	
	
}
