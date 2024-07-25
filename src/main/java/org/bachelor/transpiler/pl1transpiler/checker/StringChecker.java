/*
 * 
 */
package org.bachelor.transpiler.pl1transpiler.checker;

/**
 * This Checker Class identifies the assigned value 
 * of the Variable from PL/I and evaluates if it's a
 * allowed value for the type CHAR, or not.
 * Currently it only checks if the assigned value start with
 * a single quotation mark. This means only if a value
 * is directly assigned the Typchecker will identify the error.
 * 
 * <h3>Example</h3>
 * For example a wrong PL/I Char-Assignment are:
 * 
 * <code>DCL var_1 CHAR(4) INIT(4);</code> 
 * <code>DCL var_2 DECIMAL(3) INIT(2);<code>
 * <code>DCL var_3 CHAR(3) INIT(var_2);</code>
 * 
 * @author Lennart Hahner
 * @version 1.0
 * 
 * TODO Also check Assignments from other Variables from another type.
 */
public class StringChecker implements ITypeExpression {
	
	/** The assignment from the PL/I-Code of a Char Type. */
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
	 * start with one quotation mark (e.g.: 'Hello'). 
	 * If not it will throw an illegalArgument Exception whenever
	 * there is another value defined than a string.
	 * 
	 * <h3>Example</h3>
	 * Not allowed is e.g.:
	 * <code>DCL var_1 CHAR(1) INIT(1);</code>
	 *
	 * @throws IllegalArgumentException whenever there is an illegal Value assigned.
	 */
	public void getType() throws IllegalArgumentException{
		if(!(assignment.charAt(0) == '\'')) {
			throw new IllegalArgumentException("Assignment " + assignment + "for Alphanumeric-type not allowed.");
		}
	}
}	
