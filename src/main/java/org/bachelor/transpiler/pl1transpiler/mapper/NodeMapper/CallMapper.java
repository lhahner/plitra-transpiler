package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * This class is used to translate an BooleaExpression Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * CALL proc_1; <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * proc_1(); <br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class CallMapper implements ITranslationBehavior{
	
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
		this.parameter = parameter.contains("\'") ? parameter.replaceAll("'", "\"") : parameter;
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
	public String translate(SimpleNode simpleNode) throws MappingException{
		mapCallStatement(simpleNode);
		
		if(this.getIdentifier() != null)
			return this.getIdentifier() + " "
			 	 + this.getParameter() + "; \n";
		else 
			throw new MappingException("Identifier not definied for Call-Statement" + simpleNode.toString() + " in " + this.getClass().toString());
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
		if(new Mapper().hasChildren(simpleNode)) {
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
		if (new Mapper().hasChildren(paraNode)) {
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
			
			return "(" + identifiers.get(0) + ")";
		}
	}
}
