package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.LexicalErrorException;
import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

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
public class DeclarationMapper extends Mapper implements ITranslationBehavior {

	/**
	 * These variables symbolize the structure of a java declaration. Each String is
	 * assigned to a default value, which might change during runtime.
	 */

	/** The scope of the variable, is always public if not changed. */
	private String scope = super.javaWords.PUBLIC.getValue();

	/** The type of the variable, is always void of not changed. */
	private String type = super.javaWords.VOID.getValue();

	/** The identifier of the variable. */
	private String identifier = null;

	/** The Object of a Variable from Non-primitive type. */
	private String Object;

	/** Only set if the variable is directly initialized after declaration. */
	private String initialization;

	/**
	 * Gets the scope.
	 *
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Sets the scope.
	 *
	 * @param scope the new scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
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
	 * gets the Object.
	 *
	 * @return the Object
	 */
	public String getObject() {
		return Object;
	}

	/**
	 * sets the object.
	 *
	 * @param object the new object
	 */
	public void setObject(String object) {
		Object = object;
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
	public String translate(SimpleNode simpleNode) {
		this.mapChildNodes(simpleNode);
		if (this.getObject() != null) {
			return this.getScope() + " " + this.getType() + " " + this.getIdentifier() + " = " + this.getObject() + ";";
		}
		return this.getScope() + " " + this.getType() + " " + this.getIdentifier() + ";";
	}

	/**
	 * Map child nodes. This Maps all the Child-Nodes of the VAR Node. In this it
	 * either routes the mapping to the mapType method or saves the value of the Id
	 * Node in the identifier variable.
	 *
	 * @param varNode Node in which the VAR is defined.
	 */
	public void mapChildNodes(SimpleNode varNode) {
		String identifier;

		if (super.hasChildren(varNode)) {

			for (int i = 0; i < varNode.jjtGetNumChildren(); i++) {

				SimpleNode childNode = (SimpleNode) varNode.jjtGetChild(i);

				if (childNode.getId() == super.treeSymbols.JJTTYPE) {
					try {
						this.mapType(childNode);
					} catch (TypeMappingException tme) {
						tme.printStackTrace();
					}
				}

				else if (childNode.getId() == super.treeSymbols.JJTID) {
					String[] tmp = (String[]) childNode.jjtGetValue();
					identifier = tmp[0];
					this.setIdentifier(identifier);
				}
			}
		}
	}

	/**
	 * Maps all Datatype's of the Node-Tree. This routes them to the mapping Methods
	 * inside this class. e.g.: When the Child Node of TYPE is Arithmetic it routes
	 * the mapping to mapArithmetic. This is also used by the HeadMapper Class
	 * since, there is also a type node defined.
	 *
	 * @param typeNode The Node that contains all child Nodes as type.
	 * @return The PL/I type in Java.
	 * @throws TypeMappingException Thrown whenever there is either no translation
	 *                              or no Child.
	 */
	public void mapType(SimpleNode typeNode) throws TypeMappingException {

		if (super.hasChildren(typeNode)) {

			/** Can only be one since a variable has only one type. */
			SimpleNode firstChildOfTypeNode = (SimpleNode) typeNode.jjtGetChild(0);

			if (firstChildOfTypeNode.getId() == super.treeSymbols.JJTARITHMETIC) {
				this.mapArithmetic(firstChildOfTypeNode);

			} else if (firstChildOfTypeNode.getId() == super.treeSymbols.JJTSTRING) {
				this.mapString(firstChildOfTypeNode);

			} else if (firstChildOfTypeNode.getId() == super.treeSymbols.JJTPICTUREEXPRESSION) {
				this.mapPicture((String) firstChildOfTypeNode.jjtGetValue());

			} else if (firstChildOfTypeNode.getId() == super.treeSymbols.JJTFILE) {
				this.mapFile();

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
	public void mapArithmetic(SimpleNode arithmeticNode) throws TypeMappingException {
		ArrayList<String> arithmeticAttributes = (ArrayList<String>) arithmeticNode.jjtGetValue();
		if (arithmeticAttributes != null) {

			for (String arithmeticAttribute : arithmeticAttributes) {

				if (arithmeticAttribute.equals("COMPLEX")) {

					this.setObject(super.javaWords.NEW.getValue() + " " + super.javaWords.COMPLEX.getValue() + "("
							+ getLength(arithmeticNode) + ")");

					this.setType(super.javaWords.COMPLEX.getValue());

				} else if (arithmeticAttribute.equals("BINARY")) {

					this.setObject(super.javaWords.NEW.getValue() + " " + super.javaWords.BINARY.getValue() + "("
							+ getLength(arithmeticNode) + ")");

					this.setType(super.javaWords.BINARY.getValue());

				} else {
					this.setObject(super.javaWords.NEW.getValue() + " " + super.javaWords.DECIMAL.getValue() + "("
							+ getLength(arithmeticNode) + ")");

					this.setType(super.javaWords.DECIMAL.getValue());
				}

			}

		} else {
			throw new TypeMappingException("Type has no Values on Node", arithmeticNode);
		}
	}

	/**
	 * Maps all Strings equal Node-types like #Char, #Bit, #Graphic and #Widechar.
	 * The Mapping is done in two ways. Either a Variable is declared or a Method
	 * type is defined. When a Variable is declared it uses the CHAR Object, which
	 * has to be instantiated. When a Method type is defined it does not need to be
	 * instantiated. This why the Methods behavior depends on which context it is
	 * called.
	 *
	 * @param simpleNode the Node that contains all Char-Nodes as children.
	 * @return either just CHAR or CHAR ... = new CHAR(..);
	 */
	public String mapString(SimpleNode simpleNode) {
		if (simpleNode.jjtGetParent().jjtGetParent().getId() == treeSymbols.JJTVAR) {
			this.setObject(super.javaWords.NEW.getValue() + " " + super.javaWords.CHAR_OBJECT.getValue() + "("
					+ (String) simpleNode.jjtGetValue() + ")");
			return super.javaWords.CHAR_OBJECT.getValue();
		} else {
			return super.javaWords.CHAR_OBJECT.getValue();
		}
	}

	/**
	 * Map picture.
	 *
	 * @return the Java Expression
	 * @TODO Maps the #PictureExpression Node with the PictureMapper
	 */
	public String mapPicture(String picRegex) {
		try {
			this.setObject(super.javaWords.NEW.getValue() + " " + super.javaWords.PICTURE.getValue() + "("
					+ new PictureMapper().getRegex(picRegex) + ")");

		} catch (LexicalErrorException lee) {
			lee.printStackTrace();
		}
		return super.javaWords.PICTURE.getValue();
	}

	/**
	 * Map the #FILE Node to a Java Expression. Since there can be multiple
	 * attributes defined in PL/I that does not play role related to the type, this
	 * only uses the non-primitive type of FILE.
	 *
	 * @return the Java non-primitive type File.
	 */
	public String mapFile() {
		return super.javaWords.FILE.getValue();
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
