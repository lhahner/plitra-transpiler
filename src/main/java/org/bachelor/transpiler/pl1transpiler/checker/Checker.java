/*
 * 
 */
package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;
import java.util.List;

import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * The Class checker is the Client for the Checker Module. This Class should be
 * instantiated with the root Node of the Parsetree.
 */
public class Checker {

	/**
	 * The identifier of the declared variable will contain only identifiers from
	 * type decimal
	 */
	private List<String> decimalIdentifiers = new ArrayList<String>();

	/** The string identifiers will contain only identifiers from type string. */
	private List<String> stringIdentifiers = new ArrayList<String>();

	/** The Composite Class VarChecker */
	private VarChecker varChecker = new VarChecker();

	/**
	 * Instantiates a new checker. Should only be used by the Test Class.
	 */
	public Checker() {
	}

	/**
	 * Instantiates a new checker. It will start iterating over the Parsetree and
	 * afters iterate over all the different TypeExpression Checkers defined in the
	 * list of the Composite.
	 *
	 * @param root the root Node of the Parse tree
	 */
	public Checker(SimpleNode root) {
		iterateTree(root);
		varChecker.getType();
	}

	/**
	 * This Method implements the Depth-In-First Search Algorithm recursively.
	 * Mainly this is utilized by iterating over the whole parse-tree one by one.
	 * For the specific Node in the AstMapper class it calls a class specific to the
	 * node.
	 *
	 * @param startNode The Node to begin iterating
	 */
	public void iterateTree(SimpleNode startNode) {
		if (startNode.jjtGetParent() == null) {
			this.checkDecimalDeclaration((SimpleNode) startNode);
			this.checkStringDeclaration((SimpleNode) startNode);

			if (this.hasChildren(startNode)) {
				for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
					iterateTree((SimpleNode) startNode.jjtGetChild(i));
				}
			} else {
				return;
			}
		} else {

			for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
				this.checkDecimalDeclaration((SimpleNode) startNode.jjtGetChild(i));
				this.checkStringDeclaration((SimpleNode) startNode.jjtGetChild(i));

				if (this.hasChildren(startNode.jjtGetChild(i))) {
					iterateTree((SimpleNode) startNode.jjtGetChild(i));
				}
			}
		}
	}

	/**
	 * This method will check for a declared decimal variable and will set the
	 * decimalIdentifier List with the declared variables. Is also used when there
	 * is an Assign Note and checks if one of the identifiers saved in the decimal
	 * Identifier list is assigned. Afterwards it will a an DecimalChecker
	 * Leaf-Object to the Composite Object of Type VarChecker.
	 *
	 * @param simpleNode the simple node
	 */
	public void checkDecimalDeclaration(SimpleNode simpleNode) {
		if (simpleNode.getId() == Pl1ParserTreeConstants.JJTARITHMETIC
				&& simpleNode.jjtGetParent().jjtGetParent().getId() == Pl1ParserTreeConstants.JJTVAR) {

			SimpleNode varNode = (SimpleNode) simpleNode.jjtGetParent().jjtGetParent();

			for (int i = 0; i < varNode.jjtGetNumChildren(); i++) {

				if (varNode.jjtGetChild(i).getId() == Pl1ParserTreeConstants.JJTID) {

					SimpleNode idNode = (SimpleNode) varNode.jjtGetChild(i);
					String[] idArray = (String[]) idNode.jjtGetValue();
					decimalIdentifiers.add(idArray[0]);
				}
			}

		} else if (simpleNode.getId() == Pl1ParserTreeConstants.JJTASSIGN) {

			String[] assignValues = (String[]) simpleNode.jjtGetValue();

			for (String identifier : this.decimalIdentifiers) {
				if (assignValues[0].equals(identifier)) {
					ITypeExpression decimal = new DecimalChecker(assignValues[1]);
					varChecker.addTypeExpression(decimal);
				}
			}
		}
	}

	/**
	 * This method will check for a declared string variable and will set. the
	 * stringIdentifier List with the declared variables. Is also used when there is
	 * an Assign Note and checks if one of the identifiers saved in the
	 * stringIdentifier list is assigned. Afterwards it will a an StringChecker
	 * Leaf-Object to the Composite Object of Type VarChecker.
	 *
	 * @param simpleNode the simple node
	 */
	public void checkStringDeclaration(SimpleNode simpleNode) {
		if (simpleNode.getId() == Pl1ParserTreeConstants.JJTSTRING
				&& simpleNode.jjtGetParent().jjtGetParent().getId() == Pl1ParserTreeConstants.JJTVAR) {

			SimpleNode varNode = (SimpleNode) simpleNode.jjtGetParent().jjtGetParent();

			for (int i = 0; i < varNode.jjtGetNumChildren(); i++) {

				if (varNode.jjtGetChild(i).getId() == Pl1ParserTreeConstants.JJTID) {

					SimpleNode idNode = (SimpleNode) varNode.jjtGetChild(i);
					String[] idArray = (String[]) idNode.jjtGetValue();
					stringIdentifiers.add(idArray[0]);
				}
			}

		} else if (simpleNode.getId() == Pl1ParserTreeConstants.JJTASSIGN) {

			String[] assignValues = (String[]) simpleNode.jjtGetValue();

			for (String identifier : this.stringIdentifiers) {
				if (assignValues[0].equals(identifier)) {
					ITypeExpression string = new StringChecker(assignValues[1]);
					varChecker.addTypeExpression(string);
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
