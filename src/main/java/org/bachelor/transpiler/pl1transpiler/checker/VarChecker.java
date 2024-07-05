package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;
import java.util.List;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;

/**
 * The Class VarChecker implements the Composite of the Composite 
 * Design Pattern. It has all the declared types in a list and
 * applies the used getType() method to check all the assigned 
 * values.
 */
public class VarChecker implements ITypeExpression {
	
	/** The type expressions containing all Type Objects */
	private List<ITypeExpression> typeExpressions;
	
	/**
	 * Instantiates a new VarChecker and instantiates an ArrayList.
	 */
	public VarChecker() {
		this.typeExpressions = new ArrayList<>();
	} 
	
	/**
	 * This will call all the getType method for
	 * all leafs of this Composite.
	 * The leafs can be all types used in PL/I.
	 *
	 * @return the type
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
	 * Removes the typeExpression class from the list.
	 *
	 * @param typeExpression Class that implements TypeExpression interface
	 */
	public void removeTypeExpression(ITypeExpression typeExpression) {
		typeExpressions.remove(typeExpression);
	}
	
	
}
