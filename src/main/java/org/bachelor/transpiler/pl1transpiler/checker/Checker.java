package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;
import java.util.List;

import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class Checker {

	/** The identifier of the declared variable. */
	private List<String> decimalIdentifiers = new ArrayList<String>();
	private VarChecker varChecker = new VarChecker();
	
	public Checker() {
	}
	
	public Checker(SimpleNode root) {
		iterateTree(root);
		varChecker.getType();
	}
	/**
	 * Iterate tree.
	 *
	 * @param startNode the Node to start checking for type errors.
	 * @throws NullPointerException the null pointer exception
	 */
	public void iterateTree(SimpleNode startNode) throws NullPointerException, NumberFormatException {
		if (startNode.jjtGetParent() == null) {
			this.checkDecimalDeclaration((SimpleNode)startNode);
			
			if (this.hasChildren(startNode)) {
				for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
					iterateTree((SimpleNode) startNode.jjtGetChild(i));
				}
			} else {
				return;
			}
		} else {
			
			for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
				this.checkDecimalDeclaration((SimpleNode)startNode.jjtGetChild(i));
				
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
	public void checkDecimalDeclaration(SimpleNode simpleNode) {
		if (simpleNode.getId() == Pl1ParserTreeConstants.JJTARITHMETIC 
				&& simpleNode.jjtGetParent().jjtGetParent().getId() ==  Pl1ParserTreeConstants.JJTVAR) {
			
			SimpleNode varNode = (SimpleNode) simpleNode.jjtGetParent().jjtGetParent();

			for (int i = 0; i < varNode.jjtGetNumChildren(); i++) {

				if (varNode.jjtGetChild(i).getId() == Pl1ParserTreeConstants.JJTID) {

					SimpleNode idNode = (SimpleNode) varNode.jjtGetChild(i);
					String[] idArray = (String[]) idNode.jjtGetValue();
					decimalIdentifiers.add(idArray[0]);
				}
			}
			
		} else if(simpleNode.getId() == Pl1ParserTreeConstants.JJTASSIGN) {
			
			String[] assignValues = (String [])simpleNode.jjtGetValue();
			
			for(String identifier : this.decimalIdentifiers) {
				if(assignValues[0].equals(identifier)) {
					ITypeExpression decimal = new DecimalChecker(assignValues[1]);
					varChecker.addTypeExpression(decimal);
				}
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
}
