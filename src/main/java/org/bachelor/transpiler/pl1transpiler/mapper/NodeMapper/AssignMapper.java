package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

// TODO: Auto-generated Javadoc
/**
 * The Class AssignMapper.
 */
public class AssignMapper extends Mapper implements ITranslationBehavior {

	/** The identifier. */
	private String identifier;

	/** The operator. */
	private String operator = super.javaWords.INIT.getValue();

	/** The assignment. */
	private String assignment;

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

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
	 * @param assignment the new assignment
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment.contains("'") ? assignment.replaceAll("'", "\"") : assignment;
	}

	/**
	 * Translate.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 */
	public String translate(SimpleNode simpleNode) {
		mapAssignNode(simpleNode);
		return this.getIdentifier() + this.getOperator() + "(" + this.getAssignment() + ");";

	}

	/**
	 * Map assign node.
	 *
	 * @param simpleNode the simple node
	 */
	public void mapAssignNode(SimpleNode simpleNode) {
		// TODO Different Types require different assign methods.
		String[] assignValues = this.setAssignValues(simpleNode);
		if (assignValues != null) {
			if (new SymbolTable().getBySymbol(assignValues[0]) != null) {
				this.setIdentifier(assignValues[0]);
			}
			if (assignValues[1] != null) {
				this.setAssignment(assignValues[1]);
			}
		}
	}

	/**
	 * Map calc node.
	 *
	 * @param simpleNode the simple node
	 */
	public void mapCalcNode(SimpleNode simpleNode) {
		// TODO Map calculation Node.
		return;
	}

	/**
	 * This method is used to set the variable which should be assigned and with
	 * which values the variable should be assigned. It happens that a variable is
	 * initialized directly after declaration. In this case an explicit
	 * identificator is not defined. Then we need to map the sibling Node of the the
	 * Assign Node to get the identification.
	 * 
	 * @param simpleNode the ASSIGN Node of the parsetree
	 * @return the array containing all assign information
	 */
	public String[] setAssignValues(SimpleNode simpleNode) {
		Pl1ParserTreeConstants treeSymbols = null;
		String[] assignValues = new String[2];
		assignValues = (String[]) simpleNode.jjtGetValue();
		// Case when Variable is directly initialized after declaration
		if (simpleNode.jjtGetParent().getId() == treeSymbols.JJTVAR && assignValues[0] == null) {
			SimpleNode parent = (SimpleNode) simpleNode.jjtGetParent();
			for (int i = 0; i < parent.jjtGetNumChildren(); i++) {
				if (parent.jjtGetChild(i).getId() == treeSymbols.JJTID) {
					SimpleNode sibling = (SimpleNode) parent.jjtGetChild(i);
					String[] nodeValues = (String[]) sibling.jjtGetValue();
					assignValues[0] = nodeValues[0];
				} else {
					// TODO throw error.
				}
			}
			return assignValues;
		} else {
			return assignValues;
		}
	}

}
