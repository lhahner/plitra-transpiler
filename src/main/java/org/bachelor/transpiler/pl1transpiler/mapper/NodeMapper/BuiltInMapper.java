package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

// TODO: Auto-generated Javadoc
/**
 * This class is used to translate an BooleaExpression Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * MOD(2, 3) <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * 2 % 3 <br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class BuiltInMapper implements ITranslationBehavior {

	/** The operands. */
	private ArrayList<String> operands = new ArrayList<String>();
	
	/** The operator. */
	private String operator;

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Translate.
	 *
	 * @param builtinNode the builtin node
	 * @return the string
	 * @throws MappingException the mapping exception
	 */
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

	/**
	 * Modulo mapping.
	 *
	 * @param simpleNode the simple node
	 */
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
