package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;
import java.util.List;

import org.bachelor.transpiler.pl1transpiler.mapper.AstMapper;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

// TODO: Auto-generated Javadoc
/**
 * The Class DecimalChecker. Checking from Declaration all the assigned Values.
 */
public class DecimalChecker implements ITypeExpression {

	private String assignment;

	public String getIdentifier() {
		return assignment;
	}

	public void setIdentifier(String identifier) {
		this.assignment = identifier;
	}

	public DecimalChecker(String assignement) {
		this.assignment = assignement;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public void getType() throws NumberFormatException {
		Double.parseDouble(assignment);
	}
}

