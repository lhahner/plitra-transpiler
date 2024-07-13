package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class BuiltInMapper implements ITranslationBehavior {

	private ArrayList<String> operands = new ArrayList<String>();
	private String operator;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String translate(SimpleNode builtinNode) throws MappingException {
		if (builtinNode.getId() == Pl1ParserTreeConstants.JJTBUILTIN) {

			String builtInType = (String) builtinNode.jjtGetValue();

			if (builtInType.equals("MOD")) {

				this.moduloMapping(builtinNode);
				
				return this.operands.get(0) + this.getOperator() + this.operands.get(1);
			
			} else {
				throw new MappingException("Builtin Function not known yet.");
			}
		} else {
			throw new MappingException("Wrong Node for Builtin Mapping.");
		}
	}

	public void moduloMapping(SimpleNode simpleNode) {
		this.setOperator(Template.MODULO.getValue());

		if (new Mapper().hasChildren(simpleNode)) {

			this.setParameterDefinitionList((SimpleNode) simpleNode.jjtGetChild(0));
		}
	}

	/**
	 * Sets the parameter definition list. This has to be called before calling
	 * mapParamterDefinitionList, since this method will recursively iterate over
	 * all parameter nodes.
	 * 
	 * @param paraNode the new parameter definition list
	 */
	public void setParameterDefinitionList(SimpleNode paraNode) {
		this.operands.add((String) paraNode.jjtGetValue());

		if (new Mapper().hasChildren(paraNode)) {

			setParameterDefinitionList((SimpleNode) paraNode.jjtGetChild(0));
		}
		return;
	}

}
