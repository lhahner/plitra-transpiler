package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.LexicalErrorException;
import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

// TODO: Auto-generated Javadoc
/**
 * <h1>Summary</h1> The Class DeclarationMapper will be called in the
 * TranlationBehavior Class. During Runtime the Behavior value changes to
 * DeclarationMapper. This happens whenever a VAR Node occurs in the AST.
 * 
 * <h2>Input Example</h2> This class maps a declaration expression from PL/I to
 * Java </br>
 * {@code 
 * DCL var_1 DECIMAL(1);
 * }
 */
public class DeclarationMapper implements ITranslationBehavior {

	/** The scope of the variable, is always public if not changed. */
	private String modifier = Template.PUBLIC.getValue();

	/** The type of the variable, is always void of not changed. */
	private String type = Template.VOID.getValue();

	/** The identifier of the variable. */
	private String identifier = null;

	/** Only set if the variable is directly initialized after declaration. */
	private String initialization = null;

	/**
	 * Gets the scope.
	 *
	 * @return the scope
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * Sets the scope.
	 *
	 * @param scope the new scope
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
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
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {

		this.type = type;
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
	 * Gets the initialization.
	 *
	 * @return the initialization
	 */
	public String getInitialization() {
		return initialization;
	}

	/**
	 * Sets the initialization.
	 *
	 * @param initialization the new initialization
	 */
	public void setInitialization(String initialization) {

		this.initialization = initialization;
	}

	/**
	 * This Method is part of the ITranslationBehvior interface. It will be called
	 * whenever the Behavior in class @see TranslationMapper is set to
	 * DeclarationMapper. Check also @see astMapper for other behavior references.
	 *
	 * @param simpleNode Node in which the VAR is defined.
	 * @return the variable declaration in Java.
	 */
	public String translate(SimpleNode simpleNode) throws MappingException {
		String construktor = this.mapChildNodes(simpleNode);
		return this.getModifier() + " " + this.getType() + " " + this.getIdentifier() + construktor;
	}

	/**
	 * Map child nodes. This Maps all the Child-Nodes of the VAR Node. In this it
	 * either routes the mapping to the mapType method or saves the value of the Id
	 * Node in the identifier variable.
	 *
	 * @param varNode Node in which the VAR is defined.
	 */
	public String mapChildNodes(SimpleNode varNode) throws MappingException {
		String identifier;
//DCL var_1 DECIMAL(4); -> VAR
		if (new Mapper().hasChildren(varNode)) {

			for (int i = 0; i < varNode.jjtGetNumChildren(); i++) {

				SimpleNode childNode = (SimpleNode) varNode.jjtGetChild(i);

				if (childNode.getId() == Pl1ParserTreeConstants.JJTTYPE) {
					try {
						return this.mapType(childNode);

					} catch (Exception tme) {
						tme.printStackTrace();
					}
				}

				else if (childNode.getId() == Pl1ParserTreeConstants.JJTID) {

					String[] tmp = (String[]) childNode.jjtGetValue();
					identifier = tmp[0];
					this.setIdentifier(identifier);

				} else {
					throw new MappingException();
				}
			}
		}
		return "";
	}

	/**
	 * Maps all Datatype's of the Node-Tree. This routes them to the mapping Methods
	 * inside this class. e.g.: When the Child Node of TYPE is Arithmetic it routes
	 * the mapping to mapArithmetic. This is also used by the HeadMapper Class
	 * since, there is also a type node defined.
	 *
	 * @param typeNode The Node that contains all child Nodes as type.
	 * @return The PL/I type in Java.
	 * @throws TypeMappingException        Thrown whenever there is either no
	 *                                     translation or no Child.
	 * @throws LexicalErrorException
	 * @throws UnsupportedCharsetException
	 */
	public String mapType(SimpleNode typeNode)
			throws TypeMappingException, UnsupportedCharsetException, LexicalErrorException {

		if (new Mapper().hasChildren(typeNode)) {

			/** Can only be one since a variable has only one type. */
			SimpleNode firstChildOfTypeNode = (SimpleNode) typeNode.jjtGetChild(0);

			if (firstChildOfTypeNode.getId() == Pl1ParserTreeConstants.JJTARITHMETIC) {

				return this.mapArithmetic(firstChildOfTypeNode);

			} else if (firstChildOfTypeNode.getId() == Pl1ParserTreeConstants.JJTSTRING) {

				this.setType(Template.CHAR_ANNOTATION.getValue() + "(" + (String) firstChildOfTypeNode.jjtGetValue()
						+ ") " + Template.STRING.getValue());
				return ";";

			} else if (firstChildOfTypeNode.getId() == Pl1ParserTreeConstants.JJTPICTUREEXPRESSION) {
				
				String picRegex = (String) firstChildOfTypeNode.jjtGetValue();
				this.setType(Template.PICTURE.getValue());
				return " = " + Template.NEW.getValue() + " " + Template.PICTURE.getValue() + "(" + "\""
						+ new PictureMapper().getRegex((picRegex.replaceAll("'", ""))) + "\"" + ");";

			} else if (firstChildOfTypeNode.getId() == Pl1ParserTreeConstants.JJTFILE) {

				this.setType(Template.FILE.getValue());

				return " = " + Template.NEW.getValue() + " " + this.getType() + "();";

			} else {
				throw new TypeMappingException("No Type like this implemented", typeNode);
			}

		} else {
			throw new TypeMappingException("No Type definied for ", typeNode);
		}
	}

	/**
	 * Maps all Arithmetic Nodes like #Complex, #Float etc. All the properties of
	 * Arithmetic Type Nodes are Stored as Value. Since the combination of
	 * attributes that define a specific decimal type like complex numbers is not
	 * possible in Java with primitive types, it returns the Non-Primitive Object
	 * which is implemented in the PL/I Dependencies folder.
	 *
	 * @param arithmeticNode the arithmetic node
	 * @return the PL/I DECIMAL type mapped in Java.
	 * @throws TypeMappingException the type mapping exception
	 */
	public String mapArithmetic(SimpleNode arithmeticNode) throws TypeMappingException {
		ArrayList<String> arithmeticAttributes = (ArrayList<String>) arithmeticNode.jjtGetValue();
		if (arithmeticAttributes != null) {

			for (String arithmeticAttribute : arithmeticAttributes) {

				if (arithmeticAttribute.equals("COMPLEX")) {

					this.setType(Template.COMPLEX.getValue());
					return " = " + Template.NEW.getValue() + " " + Template.COMPLEX.getValue() + "("
							+ getLength(arithmeticNode) + ");";

				} else if (arithmeticAttribute.equals("BINARY")) {

					this.setType(Template.BINARY.getValue());
					return " = " + Template.NEW.getValue() + " " + Template.BINARY.getValue() + "("
							+ getLength(arithmeticNode) + ");";

				} else {
					this.setType(Template.DECIMAL_ANNOTATION.getValue() + "(" + getLength(arithmeticNode) + ") "
							+ Template.DOUBLE.getValue());
				}

			}
			return ";";

		} else {
			throw new TypeMappingException("Type has no Values on Node", arithmeticNode);
		}
	}

	/**
	 * 
	 * @param simpleNode
	 * @return
	 */
	public String getLength(SimpleNode simpleNode) {
		ArrayList<String> values = (ArrayList<String>) simpleNode.jjtGetValue();

		for (String value : values) {
			if (Character.isDigit(value.charAt(0))) {

				return value;
			}
		}
		return "";
	}

}
