package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

/**
 * <h1>Summary</h1>
 * The Class DisplayMapper will be called in the TranlationBehavior Class.
 * During Runtime the Behavior value changes to DisplayMapper. This happens
 * whenever a DISPLAY Node occurs in the AST.
 * 
 * <h2>Input Example</h2>
 * This class maps a display expression from PL/I to Java
 * </br>
 * {@code 
 * DISPLAY ('Hello World') REPLY (var_1);
 * }
 */
public class DisplayMapper extends Mapper implements ITranslationBehavior {

	/** The sysout variable contains a CLI Output print method. */
	private final String SYSOUT = super.javaWords.SYSOUT.getValue();

	/** The message variable contains the String message which should be displayed in console. */
	private String message;

	/** The sysin variable contains a CLI Input reader method. */
	private final String SYSIN = super.javaWords.SYSIN.getValue();

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
	public String translate(SimpleNode simpleNode) {
		this.mapDisplayNode(simpleNode);
		if (this.getParameter() != null) {
			return this.getSYSOUT() + "(" + this.getMessage() + "); \n" + this.getParameter() + ".init("
					+ this.getSYSIN() + "); \n ";
		} else {
			return this.getSYSOUT() + "(" + this.getMessage() + ");";
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
	private void mapDisplayNode(SimpleNode simpleNode) {

		ArrayList<String> values = (ArrayList<String>) simpleNode.jjtGetValue();
		for (String value : values) {
			if (new SymbolTable().getBySymbol(value) != null) {
				this.setParameter(value);
			} else {
				this.setMessage(value);
			}
		}
	}
}
