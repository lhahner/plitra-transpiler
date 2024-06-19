package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.IdentifierReferenceException;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

// TODO: Auto-generated Javadoc
/**
 * The Class ReferenceChecker.
 */
public class ReferenceChecker extends Checker {

	/** The check. */
	Checker check;
	
	/** The symbols. */
	SymbolTable symbols;

	/**
	 * Check proc head and foot reference.
	 *
	 * @param root the root
	 * @param procIdentifiers the proc identifiers
	 * @throws IdentifierReferenceException the identifier reference exception
	 */
	public void checkProcHeadAndFootReference(SimpleNode root, ArrayList<String[]> procIdentifiers)
			throws IdentifierReferenceException {

		for (String[] identifiers : procIdentifiers) {

			// Search if there is an end node with the required identifier
			super.searchValue(root, identifiers[0], "END");
			
			if(super.foundValue == null) {
				throw new IdentifierReferenceException("Semantic Error: No END for PROC " + identifiers[0]);
			}
			// if an identifier is not found, an procedure was not close
			else if (super.foundValue.jjtGetParent() != null) {

				// Saving the parent of the Node to check the Siblings for head of PROC
				SimpleNode parent = (SimpleNode) super.foundValue.jjtGetParent();

				for (int i = 0; i < parent.jjtGetNumChildren(); i++) {

					// Get the Head Node
					if (parent.jjtGetChild(i).toString().equals("HEAD")) {
						SimpleNode idNode = null;

						// Checking if Head has children, if not error
						if (parent.jjtGetChild(i).jjtGetChild(0) != null) {
							idNode = (SimpleNode) parent.jjtGetChild(i).jjtGetChild(0);
						} else {
							throw new IdentifierReferenceException("No Identifier set for Proc" + identifiers[0]);
						}

						// Getting the values of the Id Node
						String[] idValues = (String[]) idNode.jjtGetValue();

						// Checking if the same id is set for END as for PROC, if not error.
						if (idValues[0].equals("proc_1")) {
							return;
						} else {
							throw new IdentifierReferenceException("No Identifier for Proc.");
						}
					}
				}
			} else {
				throw new IdentifierReferenceException("No Identifier for Proc.");
			}

		}
	}
}
