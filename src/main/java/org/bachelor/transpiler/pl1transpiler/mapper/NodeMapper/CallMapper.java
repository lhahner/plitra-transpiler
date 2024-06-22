package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

// TODO: Auto-generated Javadoc
/**
 * The Class callMapper.
 */
public class CallMapper extends Mapper implements ITranslationBehavior{
	
	/** The identifier. */
	private String identifier = "";
	
	/** The parameter. */
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
	
	/** The parameter assign list. */
	private ArrayList<String> parameterAssignList = new ArrayList<String>();
	
	/**
	 * Translate.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 */
	public String translate(SimpleNode simpleNode) {
		mapCallStatement(simpleNode);
		return this.getIdentifier() + " "
			 + this.getParameter() + ";";
	}
	
	/**
	 * Map call statement.
	 *
	 * @param simpleNode the simple node
	 */
	public void mapCallStatement(SimpleNode simpleNode) {
		String identifier = (String)simpleNode.jjtGetValue();
		this.setIdentifier(identifier);
		if(super.hasChildren(simpleNode)) {
			this.setParameterDefinitionList((SimpleNode)simpleNode.jjtGetChild(0));
		}
		this.setParameter(this.mapParameterAssignlist(parameterAssignList));
		return;
	}
	
	/**
	 * Sets the parameter definition list.
	 *
	 * @param paraNode the new parameter definition list
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
	 *
	 * @param identifiers the identifiers
	 * @return the string
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
