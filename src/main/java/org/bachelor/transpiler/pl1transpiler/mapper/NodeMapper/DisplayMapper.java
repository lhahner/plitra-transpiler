package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
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
 * DISPLAY 'Hello World' <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * System.out.println("Hello World"); <br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class DisplayMapper implements ITranslationBehavior {

	/** The sysout variable contains a CLI Output print method. */
	private final String SYSOUT = Template.SYSOUT.getValue();

	/** The message variable contains the String message which should be displayed in console. */
	private String message;

	/** The sysin variable contains a CLI Input reader method. */
	private final String SYSIN = Template.SYSIN.getValue();

	/** The parameter in which the SYSIN method should save the input from user. */
	private String parameter;

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * Gets the sysout.
	 *
	 * @return the sysout
	 */
	public String getSYSOUT() {
		return this.SYSOUT;
	}

	/**
	 * Gets the sysin.
	 *
	 * @return the sysin
	 */
	public String getSYSIN() {
		return this.SYSIN;
	}

	/**
	 * This Method is part of the ITranslationBehvior interface. It will be called
	 * whenever the Behavior in class @see TranslationMapper is set to
	 * DisplayMapper. Check also @see astMapper for other behavior references.
	 *
	 * @param simpleNode Node in which the DISPLAY is define
	 * @return the print statement in Java.
	 */
	public String translate(SimpleNode simpleNode) throws MappingException{
		this.mapDisplayNode(simpleNode);
		if (this.getParameter() != null) {
			return this.getSYSOUT() + "(" + this.getMessage() + "); \n" + this.getParameter() + "= "
					+ this.getSYSIN() + "; \n ";
		} else {
			if(this.getMessage() != null)
				return this.getSYSOUT() + "(" + this.getMessage() + "); \n";
			else 
				throw new MappingException("Message not definied for Display-Statement" + simpleNode.toString() + " in " + this.getClass().toString());
		}

	}

	/**
	 * Maps display node.
	 * This will set a Message defined by the DISPLAY Statement in PL/I to
	 * the Message which will be send to console by System.out.println.
	 * If there is a REPLY statement defined and the identifier is known
	 * in the Symbol-Table then the parameter is assigned to 
	 * the input written by the user. Only if the Variable is declared before.
	 * variable.
	 *
	 * @param simpleNode the DISPLAY Node which contains values about message and the reply value.
	 */
	public void mapDisplayNode(SimpleNode simpleNode) throws MappingException {

		ArrayList<String> values = (ArrayList<String>) simpleNode.jjtGetValue();
		for (String value : values) {
			if (SymbolTable.getInstance().getBySymbol(value) != null) {
				this.setParameter(value);
			} else if (value.charAt(0) == '\"'){
				this.setMessage(value);
			}
			else {
				throw new MappingException("No Message or Parameter definied for Display Statement");
			}
		}
	}
}
