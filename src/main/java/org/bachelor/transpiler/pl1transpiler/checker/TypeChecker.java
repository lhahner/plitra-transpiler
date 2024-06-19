package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.IncompatibleTypeException;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

// TODO: Auto-generated Javadoc
/**
 * The Class TypeChecker.
 */
public class TypeChecker extends Checker{
	
	/** The declarations. */
	private ArrayList<String []> declarations = new ArrayList<String []>();
	
	/** The initalisations. */
	private ArrayList<String []> initalisations = new ArrayList<String []>();
	
	/**
	 * Check procedure type.
	 *
	 * @todo 
	 */
	public void checkProcedureType() {
		
	}
	
	/**
	 * Check id type.
	 *
	 * @param root the root
	 * @throws IncompatibleTypeException the incompatible type exception
	 */
	public void checkIdType(SimpleNode root) throws IncompatibleTypeException{
		setDeclarationIds(root);
		setInitalisationValue(root);
		
		for(String[] declaration : declarations) {
			for(String[] initalisation : initalisations) {
				if(declaration[0].equals(initalisation[0])) {
					if(declaration[1].equals("Arithmetic") && !(this.isNumber(initalisation[1]))) {
						throw new IncompatibleTypeException(declaration[0] + " has to be a number.");
					}
					else if(declaration[1].equals("String") && this.isNumber(initalisation[1])) {
						throw new IncompatibleTypeException(declaration[0] + " has to be a String.");
					}
					else {
						continue;
					}
				}
			}
		}
	}
	
	/**
	 * Sets the declaration ids.
	 *
	 * @param root the new declaration ids
	 */
	public void setDeclarationIds(SimpleNode root) {
		super.searchNode(root, "TYPE");
		for(SimpleNode node : super.foundNodes) {
			SimpleNode identifier = (SimpleNode)node.jjtGetParent().jjtGetChild(0);
			String[] identifierPropreties = (String[])identifier.jjtGetValue();
			
			SimpleNode type = (SimpleNode)node.jjtGetChild(0);
			String[] declaration = {
					identifierPropreties[0],
					type.toString()
			};
			declarations.add(declaration);
		}
		super.free();
	}
	
	/**
	 * Sets the initalisation value.
	 *
	 * @param root the new initalisation value
	 */
	public void setInitalisationValue(SimpleNode root) {
		super.searchNode(root, "INIT");
		for(SimpleNode node : super.foundNodes) {
			SimpleNode identifier = (SimpleNode)node;
			SimpleNode value = (SimpleNode)node.jjtGetChild(0);
			String[] initalisation = {
					identifier.jjtGetValue().toString(),
					value.jjtGetValue().toString()
			};
			initalisations.add(initalisation);
		}
		super.free();
	}
	
	/**
	 * Checks if is number.
	 *
	 * @param str the str
	 * @return True if has no ALphabetic value
	 */
	private boolean isNumber(String str) {
		for(int i = 0; i<str.length(); i++) {
			if(Character.isAlphabetic(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
