package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

/**
 * <h1>Summary</h1> 
 * Will be called in the TerminateMapper Class. During
 * Runtime the Behavior value changes to TerminateMapper. This happens whenever a
 * TERMINATES Node occurs in the AST.
 * 
 * This class maps a terminate expression from PL/I to Java
 * 
 * <h2>Input Example</h2> </br>
 * {@code
 * RETURN 'x';
 * }
 * or 
 * {@code
 * EXIT;
 * }
 * or
 * {@code
 * GOTO;
 * }
 */
public class TerminateMapper extends Mapper implements ITranslationBehavior {

	/** The termination type like return or continue or break */
	private String termination;
	
	/** The Object, only used when a String like 'String' is returned */
	private String Object;
	
	/** The identifier that are returned */
	private String identifier;

	/**
	 * Gets the termination.
	 *
	 * @return the termination
	 */
	public String getTermination() {
		return termination;
	}

	/**
	 * Sets the termination.
	 *
	 * @param termination the new termination
	 */
	public void setTermination(String termination) {
		this.termination = termination;
	}

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public String getObject() {
		return Object;
	}

	/**
	 * Sets the object.
	 *
	 * @param object the new object
	 */
	public void setObject(String object) {
		Object = object;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public String getValues() {
		return identifier;
	}

	/**
	 * Sets the values.
	 *
	 * @param values the new values
	 */
	public void setValues(String values) {
		this.identifier = values;
	}

	/**
	 * This Method is part of the ITranslationBehvior interface. It will be called
	 * whenever the Behavior in class @see TranslationMapper is set to
	 * TerminateMapper. Check also @see astMapper for other behavior references.
	 *
	 * @param simpleNode the terminates node
	 * @return the Java terminate expression like return, continue or break
	 */
	public String translate(SimpleNode simpleNode) {
		this.mapTerminateNode(simpleNode);
		if (this.getObject() != null) {
			return this.getTermination() + " " + this.getObject() + "(" + this.getValues() + ");";
		} else {
			return this.getTermination() + " " + this.getValues() + ";";
		}
	}

	/**
	 * This maps the different type of terminate statements from PL/I to Java.
	 * It checks the termination properties defined in the value variable of
	 * the TERMINATES node to map the correct Java expression.
	 *
	 * @param simpleNode the terminates node.
	 */
	public void mapTerminateNode(SimpleNode simpleNode) {
		ArrayList<String> valuesList = new ArrayList<String>();

		if (simpleNode.jjtGetValue() != null) {
			ArrayList<String> returnPropreties = (ArrayList<String>) simpleNode.jjtGetValue();
			if (super.hasChildren(simpleNode)) {
				this.setValuesList((SimpleNode) simpleNode.jjtGetChild(0), valuesList);
				this.setValues(mapTerminationValuesList(valuesList));
			}
			switch (returnPropreties.get(0)) {
			case "RETURN":
				this.setTermination(Template.RETURN.getInstance());
				if (!returnPropreties.get(1).equals("")) {
					this.setValues(returnPropreties.get(1));
				}
				//TODO this is implicit and should be done by a kind of search method which checks
				if (this.getValues().contains("'")) {
					this.setObject(Template.NEW.getInstance() + " " + Template.CHAR_OBJECT.getInstance()
					+ Template.INIT.getInstance());
				}
				else if(Character.isDigit(this.getValues().charAt(0))) {
					this.setObject(Template.NEW.getInstance() + " " + Template.DECIMAL.getInstance()
					+ Template.INIT.getInstance());
				}
				return;
			case "GO TO":
				//id + (); 
				this.setTermination(Template.CONTINUE.getInstance());
				this.setValues(returnPropreties.get(1));
				break;
			case "STOP":
				this.setTermination(Template.RETURN.getInstance());
				this.setValues("");
				return;
			case "EXIT":
				this.setTermination(Template.RETURN.getInstance());
				this.setValues("");
				return;
			default:
				return;
			}
		}
	}

	/**
	 * This method declares the list of returned identifiers.
	 * 
	 * @param identifiers a list of all the return identifier
	 * @return the return string
	 */
	public String mapTerminationValuesList(ArrayList<String> identifiers) {
		String parameterlist = "";
		if (identifiers.size() > 1) {
			for (String identifier : identifiers) {
				if (identifier.equals(identifiers.get(identifiers.size() - 1))) {
					parameterlist = parameterlist + identifier;
					return parameterlist;
				}
				parameterlist = parameterlist + identifier + ",";
			}
			return parameterlist;
		} else {
			return identifiers.get(0);
		}
	}

	/**
	 * Sets the return value list.
	 * This has to be called before calling mapTerminationValuesList,
	 * since this method will recursively iterate over all parameter nodes.
	 *
	 * @param simpleNode the PARA Node
	 * @param valuesList A list with all the values from the PL/I return statement 
	 */
	public void setValuesList(SimpleNode simpleNode, ArrayList<String> valuesList) {
		valuesList.add((String) simpleNode.jjtGetValue());
		if (super.hasChildren(simpleNode)) {
			setValuesList((SimpleNode) simpleNode.jjtGetChild(0), valuesList);
		}
		
		return;
	}

}
