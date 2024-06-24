package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class TerminateMapper extends Mapper implements ITranslationBehavior {

	private String termination;
	private String Object;
	private String values;

	public String getTermination() {
		return termination;
	}

	public void setTermination(String termination) {
		this.termination = termination;
	}

	public String getObject() {
		return Object;
	}

	public void setObject(String object) {
		Object = object;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String translate(SimpleNode simpleNode) {
		this.mapTerminateNode(simpleNode);
		if (this.getObject() != null) {
			return this.getTermination() + " " + this.getObject() + "(" + this.getValues() + ");";
		} else {
			return this.getTermination() + " " + this.getValues() + ";";
		}
	}

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
				this.setTermination(super.javaWords.RETURN.getValue());
				if (!returnPropreties.get(1).equals("")) {
					this.setValues(returnPropreties.get(1));
				}
				if (this.getValues().contains("'")) {
					String instantiator = super.javaWords.NEW.getValue() + " " + super.javaWords.CHAR_OBJECT.getValue()
							+ super.javaWords.INIT.getValue();
					this.setObject(instantiator);
				}
				return;
			case "GO TO":
				this.setTermination(super.javaWords.CONTINUE.getValue());
				this.setValues(returnPropreties.get(1));
				break;
			case "STOP":
				this.setTermination(super.javaWords.RETURN.getValue());
				this.setValues("");
				return;
			case "EXIT":
				this.setTermination(super.javaWords.RETURN.getValue());
				this.setValues("");
				return;
			default:
				return;
			}

		}

	}

	/**
	 * Map parameter definitionlist.
	 *
	 * @param identifiers the identifiers
	 * @return the string
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
	 * Sets the parameter definition list.
	 *
	 * @param simpleNode the new parameter definition list
	 */
	public void setValuesList(SimpleNode simpleNode, ArrayList<String> valuesList) {
		valuesList.add((String) simpleNode.jjtGetValue());
		if (super.hasChildren(simpleNode)) {
			setValuesList((SimpleNode) simpleNode.jjtGetChild(0), valuesList);
		}
		return;
	}

}
