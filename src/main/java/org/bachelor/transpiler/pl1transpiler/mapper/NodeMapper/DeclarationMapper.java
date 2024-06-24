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
 * The Class DeclarationMapper.
 */
public class DeclarationMapper extends Mapper implements ITranslationBehavior {

	/**
	 * These variables symbolize the structure of a java declaration. Each String is
	 * assigned to a default value, which might change during runtime.
	 */
	private String scope = super.javaWords.PUBLIC.getValue();

	/** The type. */
	private String type = super.javaWords.VOID.getValue();

	/** The identifier. */
	private String identifier = null;
	
	private String Object;

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
	
	public String getObject() {
		return Object;
	}

	public void setObject(String object) {
		Object = object;
	}

	/**
	 * Translate.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 */
	public String translate(SimpleNode simpleNode) {
		this.mapChildNodes(simpleNode);
		if(this.getObject() != null) {
			return this.getScope() + " " 
				     + this.getType() + " " 
				     + this.getIdentifier() + " = "
				     + this.getObject() + ";";
		}
		return this.getScope() + " " 
		     + this.getType() + " " 
		     + this.getIdentifier() + ";";
	}

	/**
	 * Map child nodes. This Maps all the Child-Nodes of the VAR Node. It it checks
	 * all child-Nodes and routes them to the translation Method.
	 *
	 * @param simpleNode the simple node
	 */
	public void mapChildNodes(SimpleNode simpleNode) {
		Pl1ParserTreeConstants TreeSymbols = null;
		String identifier = "";
		if (this.hasChildren(simpleNode)) {
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
	 * Maps all Datatype's of the Node-Tree. Basically this just routes them to the
	 * mapping Methods inside this class.
	 *
	 * @param simpleNode The Node that contains all types.
	 * @return string Java Expression.
	 * @throws TypeMappingException Thrown whenever there is either no translation
	 *                              or no Child.
	 */
	public String mapType(SimpleNode simpleNode) throws TypeMappingException {
		if (this.hasChildren(simpleNode)) {
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
					throw new TypeMappingException("No Type for ");
				}
			}
		} else {
			throw new TypeMappingException("No Type for");
		}
		return null;
	}

	/**
	 * Maps all Arithmetic Types like #Complex, #Float etc.
	 *
	 * @param simpleNode the arithmetic node
	 * @return the Java Expression
	 */
	public String mapArithmetic(SimpleNode simpleNode) throws TypeMappingException{
		ArrayList<String> typeAttributes = (ArrayList<String>)simpleNode.jjtGetValue();
		if (typeAttributes != null) {
			for (String typeAttribute : typeAttributes) {
				if (typeAttribute.equals("COMPLEX")) {
					return super.javaWords.COMPLEX.getValue();
				} else if (typeAttribute.equals("FIXED")) {
					return super.javaWords.BIGDECIMAL.getValue();
				} else if (typeAttribute.equals("BINARY")) {
					return super.javaWords.BINARY.getValue();
				} else if (typeAttribute.equals("DECIMAL")) {
					return super.javaWords.DOUBLE.getValue();
				}
			}
			throw new TypeMappingException("Arithmetic type without translateable propreteis.");
		}
		else {
			throw new TypeMappingException("Arithmetic type without translateable propreteis.");
		}
	}

	/**
	 * Maps all Strings equal Node-types like #Char, #Bit, #Graphic and #Widechar
	 *
	 * @param simpleNode the Node that contains all Char-Nodes as children.
	 * @return the Java Expression
	 */
	public String mapString(SimpleNode simpleNode) {
		Pl1ParserTreeConstants treeSymbols = null;
		String length = "";
		if(simpleNode.jjtGetValue() != null) {
			length = (String)simpleNode.jjtGetValue();
		}
		if(simpleNode.jjtGetParent().jjtGetParent().getId() == treeSymbols.JJTVAR) {
			String instantiator = super.javaWords.NEW.getValue() + " "
								+ super.javaWords.CHAR_OBJECT.getValue()
								+ "(" + length + ")";
			this.setObject(instantiator);
			return super.javaWords.CHAR_OBJECT.getValue();
		}
		else {
			return super.javaWords.CHAR_OBJECT.getValue();
		}
	}

	/**
	 * Maps the #LOCATOR Node to a Java Expression.
	 *
	 * @return the Java Expression
	 */
	public String mapLocator() {
		return super.javaWords.OBJECT.getValue();
	}

	/**
	 * Maps the #PictureExpression Node with the @todo PictureMapper
	 *
	 * @return the Java Expression
	 */
	public String mapPicture() {
		return null;
	}

	/**
	 * Map the #FILE Node to a Java Expression.
	 *
	 * @return the Java Expression
	 */
	public String mapFile() {
		return super.javaWords.FILE.getValue();
	}

	/**
	 * Maps the #ENTRY Node to a Java.
	 *
	 * @return Java Expression
	 */
	public String mapEntry() {
		return null;
	}

	/**
	 * Checks if a Node has children by checking the number of children provided by
	 * the SimpleNode class.
	 *
	 * @param nodeToCheck Node that should be checked for children Nodes.
	 * @return True if number of Children is larger than 0.
	 */
	public boolean hasChildren(Node nodeToCheck) {
		if (nodeToCheck.jjtGetNumChildren() > 0) {
			return true;
		} else {
			return false;
		}
	}
}

