package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

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
	 * @param simpleNode Node in which the VAR is defined.
	 */
	public void mapChildNodes(SimpleNode simpleNode) {
		Pl1ParserTreeConstants TreeSymbols = null;
		String identifier = "";
		if (super.hasChildren(simpleNode)) {
			for (int i = 0; i < simpleNode.jjtGetNumChildren(); i++) {
				SimpleNode childNode = (SimpleNode) simpleNode.jjtGetChild(i);
				if (childNode.getId() == TreeSymbols.JJTTYPE) {
					try {
						this.setType(this.mapType(childNode));
					} catch (TypeMappingException tme) {
						tme.printStackTrace();
					}
				} else if (childNode.getId() == TreeSymbols.JJTID) {
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
	 * @param simpleNode The Node that contains all child Nodes as type.
	 * @return The PL/I type in Java.
	 * @throws TypeMappingException Thrown whenever there is either no translation
	 *                              or no Child.
	 */
	public String mapType(SimpleNode simpleNode) throws TypeMappingException {
		if (super.hasChildren(simpleNode)) {
			for (int i = 0; i < simpleNode.jjtGetNumChildren(); i++) {
				SimpleNode childNode = (SimpleNode) simpleNode.jjtGetChild(i);
				if (childNode.toString().equals("Arithmetic")) {
					return this.mapArithmetic(childNode);
				} else if (childNode.toString().equals("String")) {
					return this.mapString(childNode);
				} else if (childNode.toString().equals("Locator")) {
					return this.mapLocator();
				} else if (childNode.toString().equals("PictureExpression")) {
					return this.mapPicture();
				} else if (childNode.toString().equals("File")) {
					return this.mapFile();
				} else if (childNode.toString().equals("Entry")) {
					return this.mapEntry();
				} else {
					throw new TypeMappingException("No Type for the desired input.");
				}
			}
		} else {
			throw new TypeMappingException("No Type for the desired input.");
		}
		return null;
	}

	/**
	 * Maps all Arithmetic Nodes like #Complex, #Float etc. All the properties of
	 * Arithmetic Type Nodes are Stored as Value. Since the combination of
	 * attributes that define a specific decimal type like complex numbers is not
	 * possible in Java with primitive types, it returns the Non-Primitive Object
	 * which is implemented in the PL/I Dependencies folder.
	 *
	 * @param simpleNode the arithmetic node
	 * @return the PL/I DECIMAL type mapped in Java.
	 * @throws TypeMappingException the type mapping exception
	 */
	public String mapArithmetic(SimpleNode simpleNode) throws TypeMappingException {
		ArrayList<String> typeAttributes = (ArrayList<String>) simpleNode.jjtGetValue();
		if (typeAttributes != null) {
			for (String typeAttribute : typeAttributes) {
				if (typeAttribute.equals("COMPLEX")) {
					return this.mapDecimal(simpleNode, super.javaWords.COMPLEX.getValue());
				} else if (typeAttribute.equals("FIXED")) {
					return this.mapDecimal(simpleNode, super.javaWords.DECIMAL.getValue());
				} else if (typeAttribute.equals("BINARY")) {
					return this.mapDecimal(simpleNode, super.javaWords.BINARY.getValue());
				} else if (typeAttribute.equals("DECIMAL")) {
					return this.mapDecimal(simpleNode, super.javaWords.DECIMAL.getValue());
				}
			}
			throw new TypeMappingException("Arithmetic type without translateable propreteis.");
		} else {
			throw new TypeMappingException("Arithmetic type without translateable propreteis.");
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
		Pl1ParserTreeConstants treeSymbols = null;
		String length = "";
		if (simpleNode.jjtGetValue() != null) {
			length = (String) simpleNode.jjtGetValue();
		}
		if (simpleNode.jjtGetParent().jjtGetParent().getId() == treeSymbols.JJTVAR) {
			this.setObject(
					super.javaWords.NEW.getValue() + " " + super.javaWords.CHAR_OBJECT.getValue() + "(" + length + ")");
			return super.javaWords.CHAR_OBJECT.getValue();
		} else {
			return super.javaWords.CHAR_OBJECT.getValue();
		}
	}
	
	/**
	 * 
	 * @param simpleNode
	 * @return
	 */
	public String mapDecimal(SimpleNode simpleNode, String type) {
		Pl1ParserTreeConstants treeSymbols = null;
		ArrayList<String> values = (ArrayList<String>) simpleNode.jjtGetValue();

		for (String value : values) {
			if (Character.isDigit(value.charAt(0))) {
				this.setObject(super.javaWords.NEW.getValue() + " " + type + "(" + value + ")");
				return type;
			}
			else {
				//TODO throw error
			}
		}
		return type;
	}
	
	/**
	 * Map locator.
	 *
	 * @return the Java Expression
	 * @TODO Maps the #LOCATOR Node to a Java Expression.
	 */
	public String mapLocator() {
		return super.javaWords.OBJECT.getValue();
	}

	/**
	 * Map picture.
	 *
	 * @return the Java Expression
	 * @TODO Maps the #PictureExpression Node with the PictureMapper
	 */
	public String mapPicture() {
		return null;
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
	 * Map entry.
	 *
	 * @return Java Expression
	 * @TODO Maps the #ENTRY Node to a Java.
	 */
	public String mapEntry() {
		return null;
	}
}
