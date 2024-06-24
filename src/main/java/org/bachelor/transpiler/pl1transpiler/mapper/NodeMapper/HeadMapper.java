package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * The Class HeadMapper.
 * This Class Maps the Head Node with a Java equivalent method head.
 * The type of the method is not mapped here. For this @see TypeMapper.
 */
public class HeadMapper extends Mapper implements ITranslationBehavior {

	/** The scope. */
	private final String SCOPE = super.javaWords.PUBLIC.getValue();

	/** The type. */
	private String type = super.javaWords.VOID.getValue();
	
	/** The identifier. */
	private String identifier = "";
	
	/** The parameter. */
	private String parameter = "()";
	
	/** The parameter declaration list. */
	private ArrayList<String> parameterDeclarationList = new ArrayList<String>();
	
	public String getSCOPE() {
		return SCOPE;
	}
	
	public String getType() {
		return type;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * Translate.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 */
	public String translate(SimpleNode simpleNode) {
		mapHeadNode(simpleNode);
		return this.getSCOPE() + " "
			+  this.getType() + " "
			+  this.getIdentifier() + " "
			+  this.getParameter() + "{ \n";
	}

	/**
	 * Map head node.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 */
	public void mapHeadNode(SimpleNode simpleNode) {
		if (super.hasChildren(simpleNode)) {
			for (int i = 0; i < simpleNode.jjtGetNumChildren(); i++) {
				SimpleNode childNode = (SimpleNode) simpleNode.jjtGetChild(i);
				switch (childNode.toString()) {
				case "Id":
					String[] tmp = (String[]) childNode.jjtGetValue();
					this.setIdentifier(tmp[0]);
					break;
				case "PARA":
					this.setParameterDefinitionList(childNode);
					this.parameter = mapParameterDefinitionlist(parameterDeclarationList);
					break;
				case "OPTIONS":
					break;
				case "RETURNS":
					try {
						if(super.hasChildren(childNode)) {
							this.setType(new DeclarationMapper()
									.mapType((SimpleNode)childNode.jjtGetChild(0)));
						}
					}
					catch(TypeMappingException tme) {
						tme.printStackTrace();
					}
				default:
					return; //@todo throw Exception
				}
			}
		}
	}

	/**
	 * Map parameter definitionlist.
	 *
	 * @param identifiers the identifiers
	 * @return the string
	 */
	public String mapParameterDefinitionlist(ArrayList<String> identifiers) {
		String parameterlist = "(";
		if (identifiers.size() > 1) {
			for (String identifier : identifiers) {
				if (identifier.equals(identifiers.get(identifiers.size() - 1))) {
					parameterlist = parameterlist + super.javaWords.OBJECT.getValue() + " " + identifier;
					return parameterlist + ")";
				}
				parameterlist = parameterlist + super.javaWords.OBJECT.getValue() + " " + identifier + ",";
			}
			return parameterlist + ")";
		} else {
			this.parameterDeclarationList.clear();
			return "(" + super.javaWords.OBJECT.getValue() + " " + identifiers.get(0) + ")";
		}
	}

	/**
	 * Sets the parameter definition list.
	 *
	 * @param paraNode the new parameter definition list
	 */
	public void setParameterDefinitionList(SimpleNode paraNode) {
		this.parameterDeclarationList.add((String) paraNode.jjtGetValue());
		if (super.hasChildren(paraNode)) {
			setParameterDefinitionList((SimpleNode) paraNode.jjtGetChild(0));
		}
		return;
	}
}
