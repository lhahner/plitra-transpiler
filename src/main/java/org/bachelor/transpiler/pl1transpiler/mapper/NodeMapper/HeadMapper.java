package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

/**
 * <h1>Summary</h1>
 * Will be called in the TranlationBehavior Class.
 * During Runtime the Behavior value changes to HeadMapper. This happens
 * whenever a HEAD Node occurs in the AST.
 * 
 * This class maps a procedure head expression from PL/I to Java 
 * 
 * <h2>Input Example</h2>
 * </br>
 * {@code
 * proc_1 PROC(para_1, para_2) RETURNS (CHAR(5));
 * 
 * }
 * 
 */
public class HeadMapper implements ITranslationBehavior {

	/**  The scope of the mapped method. */
	private final String SCOPE = Template.PUBLIC.getValue();

	/**  The type of the mapped method. */
	private String type = Template.VOID.getValue();
	
	/**  The identifier of the mapped method. */
	private String identifier = "";
	
	/**  The parameter of the mapped method. */
	private String parameter = "()";
	
	/** The parameter declaration list. */
	private ArrayList<String> parameterDeclarationList = new ArrayList<String>();
	
	/**
	 * Gets the scope.
	 *
	 * @return the scope
	 */
	public String getSCOPE() {
		return SCOPE;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

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
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
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

	/**
	 * This Method is part of the ITranslationBehvior interface. It will be called
	 * whenever the Behavior in class @see TranslationMapper is set to
	 * HeadMapper. Check also @see astMapper for other behavior references.
	 *
	 * @param simpleNode the head node
	 * @return the Java Method head node
	 */
	public String translate(SimpleNode simpleNode) {
		mapHeadNode(simpleNode);
		return this.getSCOPE() + " "
			+  this.getType() + " "
			+  this.getIdentifier() + " "
			+  this.getParameter();
	}

	/**
	 * This Method iterates over all child Nodes of the Head node. 
	 * It set the identifier if found and map the parameter with mapParameterDefinition list
	 * It defines the of the method type by the returns Node. which will lead to the call 
	 * of the mapType method in declaration Mapper class.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 */
	public void mapHeadNode(SimpleNode simpleNode) {
		if (new Mapper().hasChildren(simpleNode)) {
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
					this.parameterDeclarationList.clear();
					break;
				case "OPTIONS":
					//TODO
					break;
				case "RETURNS":
					try {
						if(new Mapper().hasChildren(childNode)) {
							new DeclarationMapper()
									.mapType((SimpleNode)childNode.jjtGetChild(0));
						}
					}
					catch(TypeMappingException tme) {
						tme.printStackTrace();
					}
				default:
					return; //TODO
				}
			}
		}
	}

	/**
	 * This method declares the parameter definition list of a method
	 * it will always define the parameter in type Object since in PL/I
	 * no types are defined and implicitly generated.
	 *
	 * @param identifiers a list of all the parameter identifier
	 * @return the parameter string
	 */
	public String mapParameterDefinitionlist(ArrayList<String> identifiers) {
		String parameterlist = "(";
		if (identifiers.size() > 1) {
			for (String identifier : identifiers) {
				if (identifier.equals(identifiers.get(identifiers.size() - 1))) {
					parameterlist = parameterlist + Template.OBJECT.getValue() + " " + identifier;
					return parameterlist + ")";
				}
				parameterlist = parameterlist + Template.OBJECT.getValue() + " " + identifier + ",";
			}
			return parameterlist + ")";
		} else {
			
			return "(" + Template.OBJECT.getValue() + " " + identifiers.get(0) + ")";
		}
	}

	/**
	 * Sets the parameter definition list.
	 * This has to be called before calling mapParamterDefinitionList,
	 * since this method will recursively iterate over all parameter nodes.
	 * @param paraNode the new parameter definition list
	 */
	public void setParameterDefinitionList(SimpleNode paraNode) {
		this.parameterDeclarationList.add((String) paraNode.jjtGetValue());
		if (new Mapper().hasChildren(paraNode)) {
			setParameterDefinitionList((SimpleNode) paraNode.jjtGetChild(0));
		}
		return;
	}
}
