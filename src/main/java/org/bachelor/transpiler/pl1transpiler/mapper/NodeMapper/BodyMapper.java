package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * <h1>Summary</h1>
 * The Class BodyMapper will be called the strategy of the TranlationBehavior
 * Object changes to BodyMapper. This happens whenever a Body Node occurs in the
 * AST. The BodyMapper only defines the structure of the Body, the mapping of
 * the Child Nodes is done separately in the class. @see astMapper.
 * 
 * This class is for Program-Structure purpose only and will not
 * translate any expression.
 */
public class BodyMapper extends Mapper implements ITranslationBehavior {

	/**
	 * Defines the outline, is default value '{}' when there is no content in
	 * PL/I-procedure body.
	 */
	private String body = "{}";

	/**
	 * Basic getter for String body.
	 * 
	 * @return String body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Basic setter for String body
	 * 
	 * @param value for the body, either { or default.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * This Method is part of the ITranslationBehvior interface. It will be called
	 * whenever the Behavior in class @see TranslationMapper is set to BodyMapper.
	 * Check also @see astMapper for other behavior references.
	 * 
	 * @param simpleNode Node in which the BODY is defined.
	 * @return equivalent Java Program structure.
	 */
	public String translate(SimpleNode simpleNode) {
		if (super.hasChildren(simpleNode)) {
			this.setBody("{");
			return this.getBody();
		} else {
			return this.getBody();
		}
	}
}
