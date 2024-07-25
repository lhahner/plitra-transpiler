package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

/**
 * This class is used to translate an BooleaExpression Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * RETURN 1 <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * retrun 1; <br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class TerminateMapper implements ITranslationBehavior {

	/** The termination type like return or continue or break */
	private String termination;

	/** The identifier that are returned */
	private String value;

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
	 * Gets the values.
	 *
	 * @return the values
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the values.
	 *
	 * @param values the new values
	 */
	public void setValue(String values) {
		this.value = values;
	}

	/**
	 * This Method is part of the ITranslationBehvior interface. It will be called
	 * whenever the Behavior in class @see TranslationMapper is set to
	 * TerminateMapper. Check also @see astMapper for other behavior references.
	 *
	 * @param simpleNode the terminates node
	 * @return the Java terminate expression like return, continue or break
	 */
	public String translate(SimpleNode simpleNode) throws MappingException {
		this.mapTerminateNode(simpleNode);

		if (this.getTermination() != null && this.getValue() != null) {
			return this.getTermination() + " " + this.getValue() + "; \n";
		
		} else if(this.getTermination() != null && this.getValue() == null) {
			return this.getTermination() + " ; \n";
		
		} else {
			throw new MappingException("Value and Termination type not definied for Termination for "
					+ simpleNode.toString() + " in " + this.getClass().toString());
		}
	}

	/**
	 * This maps the different type of terminate statements from PL/I to Java. It
	 * checks the termination properties defined in the value variable of the
	 * TERMINATES node to map the correct Java expression.
	 *
	 * @param simpleNode the terminates node.
	 */
	public void mapTerminateNode(SimpleNode simpleNode) {
		ArrayList<String> valuesList = new ArrayList<String>();

		if (simpleNode.jjtGetValue() != null) {
			ArrayList<String> returnPropreties = (ArrayList<String>) simpleNode.jjtGetValue();
			if (new Mapper().hasChildren(simpleNode)) {
				this.setValuesList((SimpleNode) simpleNode.jjtGetChild(0), valuesList);
				this.setValue(mapTerminationValuesList(valuesList));
			}
			switch (returnPropreties.get(0)) {
			case "RETURN":
				this.setTermination(Template.RETURN.getValue());

				if (!returnPropreties.get(1).equals("")) {
					if (new HeadMapper().getIdentifier().equals(returnPropreties.get(1))) {
						this.setValue("(" + new HeadMapper().getType() + ")" + returnPropreties.get(1));
						return;
					}
					this.setValue(returnPropreties.get(1));
				}

				return;
			case "GO TO":
				// to be discussed
				this.setTermination(Template.CONTINUE.getValue());
				this.setValue(returnPropreties.get(1));
				break;
			case "STOP":
				this.setTermination(Template.RETURN.getValue());
				this.setValue("");
				return;
			case "EXIT":
				this.setTermination(Template.RETURN.getValue());
				this.setValue("");
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
	 * Sets the return value list. This has to be called before calling
	 * mapTerminationValuesList, since this method will recursively iterate over all
	 * parameter nodes.
	 *
	 * @param simpleNode the PARA Node
	 * @param valuesList A list with all the values from the PL/I return statement
	 */
	public void setValuesList(SimpleNode simpleNode, ArrayList<String> valuesList) {
		valuesList.add((String) simpleNode.jjtGetValue());
		if (new Mapper().hasChildren(simpleNode)) {
			setValuesList((SimpleNode) simpleNode.jjtGetChild(0), valuesList);
		}

		return;
	}

}
