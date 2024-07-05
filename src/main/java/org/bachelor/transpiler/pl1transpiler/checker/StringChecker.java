/*
 * 
 */
package org.bachelor.transpiler.pl1transpiler.checker;

/**
 * The Leaf-Class StringChecker will
 * check if the assignment value start with
 * a single quotation mark.
 */
public class StringChecker implements ITypeExpression {
	
	/** The assignment of a String Type. */
	private String assignment;
	
	/**
	 * Gets the assignment.
	 *
	 * @return the assignment
	 */
	public String getAssignement() {
		return assignment;
	}

	/**
	 * Sets the assignment.
	 *
	 * @param assignment the new assignment
	 */
	public void setAssignement(String assignement) {
		this.assignment = assignement;
	}

	/**
	 * Instantiates a new string checker.
	 *
	 * @param assignment the assignment
	 */
	public StringChecker(String assignment) {
		this.assignment = assignment;
	}
	
	/**
	 * Gets the type and checks if the assignment value
	 * start with one quotation mark. 
	 * If not it will throw an illegalArgument Exception whenever
	 * there is an other value defined than a string.
	 * This method is also important because in the mapped module
	 * the single quotation mark is changed to a double quotation mark.
	 * 
	 * <h3>Example</h3>
	 * Not allowed is e.g.:
	 * <code>DCL var_1 CHAR(1) INIT(1);</code>
	 *
	 * @throws IllegalArgumentException whenever there is an illegal Value assigned.
	 */
	public void getType() throws IllegalArgumentException{
		if(!(assignment.charAt(0) == '\'')) {
			throw new IllegalArgumentException("Assignment for String Type not allowed.");
		}
	}
}	
