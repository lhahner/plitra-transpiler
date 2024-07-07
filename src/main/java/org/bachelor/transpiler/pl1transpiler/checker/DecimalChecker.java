/*
 * 
 */
package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;
import java.util.List;

import org.bachelor.transpiler.pl1transpiler.mapper.AstMapper;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

/**
 * The Leaf-Class DecimalChecker will
 * check if the assignment value can be parsed into a 
 * primitive type double.
 */
public class DecimalChecker implements ITypeExpression {

	/** The assignment of an Decimal Type */
	private String assignment;

	/**
	 * Gets the assignment.
	 *
	 * @return the assignment
	 */
	public String getAssignment() {
		return assignment;
	}

	/**
	 * Sets the assignment.
	 *
	 * @param identifier the new assignment
	 */
	public void setIdentifier(String identifier) {
		this.assignment = identifier;
	}

	/**
	 * Instantiates a new decimal checker.
	 *
	 * @param assignment the assignment of a variable
	 */
	public DecimalChecker(String assignement) {
		this.assignment = assignement;
	}

	/**
	 * The getType method should check if the 
	 * assigned value is in numeric format.
	 * If not it will throw an Exception.
	 * 
	 * <h3>Example</h3>
	 * Not allowed is e.g.:
	 * <code>DCL var_1 DECIMAL(1) INIT('t');</code>
	 *
	 * @throws NumberFormatException if the assignment is not allowed
	 */
	public void getType() throws NumberFormatException {
		try {
			Double.parseDouble(assignment);
		} catch(NumberFormatException nfe) {
			throw new NumberFormatException("Assignement: " + assignment + " Not allowed for this Type.");
		}
	}
}

