package org.bachelor.transpiler.pl1transpiler.checker;

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

	/** Constant which defines Leaf of Composite. */
	private final static int TYPE = Pl1ParserTreeConstants.JJTARITHMETIC;

	/** The identifier of the declared variable. */
	private List<String> identifier;

	/**
	 * Iterate tree.
	 *
	 * @param startNode the Node to start checking for type errors.
	 * @throws NullPointerException the null pointer exception
	 */
	public void iterateTree(SimpleNode startNode) throws NullPointerException {
		if (startNode.jjtGetParent() == null) {
			this.checkDecimal(startNode);
			
			if (this.hasChildren(startNode)) {
				for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
					iterateTree((SimpleNode) startNode.jjtGetChild(i));
				}
			} else {
				return;
			}
		} else {
			
			for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
				this.checkDecimal(startNode);
				
				if (this.hasChildren(startNode.jjtGetChild(i))) {
					iterateTree((SimpleNode) startNode.jjtGetChild(i));
				}
			}
		}
	}

	/**
	 * This method will check for a declared decimal variable and will set
	 *
	 * @param simpleNode the simple node
	 */
	public void checkDecimal(SimpleNode simpleNode) {
		if (simpleNode.getId() == TYPE && simpleNode.jjtGetParent().jjtGetParent().getId() == VarChecker.STORAGETYPE) {
			this.checkDeclaration(simpleNode);
			
		} else if (simpleNode.getId() == Pl1ParserTreeConstants.JJTASSIGN) {
			String[] assignedValues = (String[]) simpleNode.jjtGetValue();

			for (String identifier : this.identifier) {
				
				if (identifier.equals(assignedValues[0])) {
					this.getType(assignedValues[1]);
				}
			}
		}
	}

	/**
	 * Check declaration.
	 *
	 * @param simpleNode the simple node
	 */
	public void checkDeclaration(SimpleNode simpleNode) {

		SimpleNode varNode = (SimpleNode) simpleNode.jjtGetParent().jjtGetParent();

		for (int i = 0; i < varNode.jjtGetNumChildren(); i++) {

			if (varNode.jjtGetChild(i).getId() == Pl1ParserTreeConstants.JJTID) {

				SimpleNode idNode = (SimpleNode) varNode.jjtGetChild(i);
				String[] idArray = (String[]) idNode.jjtGetValue();
				this.identifier.add(idArray[0]);

			}
		}
	}

	/**
	 * Checks if a Node has children by checking the number of children provided by
	 * the SimpleNode class.
	 *
	 * @param nodeToCheck Node that should be checked for children Nodes.
	 * @return True if number of Children is larger than 0.
	 */
	public boolean hasChildren(Node nodeToCheck) {
		
		if (nodeToCheck.jjtGetNumChildren() > 0) {
			return true;
		
		} else {
			return false;
		}
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public void getType(String assignment) {
		try {
			Double.parseDouble(assignment);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			
		}

	}
}
