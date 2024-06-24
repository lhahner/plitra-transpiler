package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * <h1>Summary</h1>
 * The Class CallMapper will be called the strategy of the TranlationBehavior Object
 * changes to CallMapper. This happens whenever a CALL Node occurs in the AST.
 * This class maps a CALL expression from PL/I.
 * 
 * <h2>Input Example</h2>
 * </br>
 * {@code 
 * 		CALL proc_1();
 * or
 * 		CALL proc_2(para_1, para_2);
 * }
 */
public class CallMapper extends Mapper implements ITranslationBehavior{
	
	/** identifier of the calling method*/
	private String identifier = "";
	
	/** The parameter of the calling method. */
	private String parameter = "()";
	
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
	 * Gets the parameter.
	 *
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * Sets the parameter.
	 *
	 * @param parameter the new parameter
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	/** List of Parameter which are assigned by a method call.
	 *  e.g.: CALL proc_1('para_1', 'para_2'); */
	private ArrayList<String> parameterAssignList = new ArrayList<String>();
	
	/**
	 * This Method is part of the ITranslationBehvior interface. It will be called
	 * whenever the Behavior in class @see TranslationMapper is set to CallMapper.
	 * Check also @see astMapper for other behavior references.
	 *
	 * @param simpleNode Node in which the CALL is defined.
	 * @return the method to be called in Java.
	 */
	public String translate(SimpleNode simpleNode) {
		mapCallStatement(simpleNode);
		return this.getIdentifier() + " "
			 + this.getParameter() + ";";
	}
	
	/**
	 * Maps all the child elements and values from the Parse-Tree
	 * with the fitting Java-Macros defined in @see Template.
	 *
	 * @param simpleNode		Node in which the CALL is defined.
	 */
	public void mapCallStatement(SimpleNode simpleNode) {
		String identifier = (String)simpleNode.jjtGetValue();
		this.setIdentifier(identifier);
		if(super.hasChildren(simpleNode)) {
			this.setParameterDefinitionList((SimpleNode)simpleNode.jjtGetChild(0));
			this.setParameter(this.mapParameterAssignlist(parameterAssignList));
		}
		return;
	}
	
	/**
	 * Sets the parameter definition list.
	 * Which is a list that contains all the parameter of a method.
	 * Calls itself recursively and iterates through all child nodes,
	 * that define a parameter for the method.
	 *
	 * @param paraNode		Parameter Node of the parse tree, should contain identifier as value.
	 */
	public void setParameterDefinitionList(SimpleNode paraNode) {
		this.parameterAssignList.add((String) paraNode.jjtGetValue());
		if (super.hasChildren(paraNode)) {
			setParameterDefinitionList((SimpleNode) paraNode.jjtGetChild(0));
		}
		return;
	}
	
	/**
	 * Map parameter assignlist. 
	 * This Maps the Parameter list in the format that Java requires.
	 * e.g.: (para_1, para_2)
	 * This method should be called after setting the setParameterDefinitionList.
	 * Without it will return null.
	 * 
	 * @param identifiers		List of identifiers defined in the parameter-list.
	 * @return a string which will be the parameter-list for the calling method.
	 */
	public String mapParameterAssignlist(ArrayList<String> identifiers) {
		String parameterlist = "(";
		if (identifiers.size() > 1) {
			for (String identifier : identifiers) {
				if (identifier.equals(identifiers.get(identifiers.size() - 1))) {
					parameterlist = parameterlist + identifier;
					return parameterlist + ")";
				}
				parameterlist = parameterlist + identifier + ",";
			}
			return parameterlist + ")";
		} else {
			this.parameterAssignList.clear();
			return "(" + identifiers.get(0) + ")";
		}
	}
}
