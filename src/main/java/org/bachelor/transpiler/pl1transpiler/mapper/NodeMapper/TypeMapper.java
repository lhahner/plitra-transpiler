package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

// TODO: Auto-generated Javadoc
/**
 * @deprecated
 * The Class TypeMapper.
 */
public class TypeMapper extends VarMapper {

	/**
	 * 
	 * Map type.
	 * @param typeNode the type node
	 * @return the string
	 * @throws TypeMappingException the type mapping exception
	 */
	public String mapType(SimpleNode typeNode) throws TypeMappingException {
		if (super.hasChildren(typeNode)) {
			for (int i = 0; i < typeNode.jjtGetNumChildren(); i++) {
				
				SimpleNode childNode = (SimpleNode) typeNode.jjtGetChild(i);
				switch (childNode.toString()) {
					case "Arithmetic":
						return this.mapArithmetic(childNode);
					case "String":
						return this.mapString(childNode);
					case "Locator":
						return this.mapLocator();
					case "PictureExpression":
						return this.mapPicture();
					case "File":
						return this.mapFile();
					case "Entry":
						return this.mapEntry();
					default:
						throw new TypeMappingException("No Type for ");
				}
			}
		} else {
			throw new TypeMappingException("No Type for");
		}
		return null;
	}

	/**
	 * Map arithmetic.
	 *
	 * @param arithmeticNode the arithmetic node
	 * @return the string
	 */
	public String mapArithmetic(SimpleNode arithmeticNode) {

		for (int i = 0; i < arithmeticNode.jjtGetNumChildren(); i++) {
			SimpleNode childNode = (SimpleNode) arithmeticNode.jjtGetChild(i);
			switch (childNode.toString()) {
			case "Complex":
				return super.javaWords.COMPLEX.getValue();
			case "Float":
				return super.javaWords.FLOAT.getValue();
			case "Fixed":
				return super.javaWords.BIGDECIMAL.getValue();
			case "Binary":
				return super.javaWords.BINARY.getValue();

			case "Decimal":
				return super.javaWords.DOUBLE.getValue();
			default:
				break;
			}
		}
		return null;
	}

	/**
	 * Map string.
	 *
	 * @param stringNode the string node
	 * @return the string
	 */
	public String mapString(SimpleNode stringNode) {
		for (int i = 0; i < stringNode.jjtGetNumChildren(); i++) {
			SimpleNode childNode = (SimpleNode) stringNode.jjtGetChild(i);
				switch (childNode.toString()) {
				case "Char":
					return super.javaWords.CHAR_OBJECT.getValue();
				case "Bit":
					return super.javaWords.CHAR.getValue();
				case "Graphic":
					return super.javaWords.CHAR_OBJECT.getValue();
				case "Widechar":
					return super.javaWords.CHAR_OBJECT.getValue();
				default:
					break;
				}
		}
		return null;
	}

	/**
	 * Map locator.
	 *
	 * @return the string
	 */
	public String mapLocator() {
		return super.javaWords.OBJECT.getValue();
	}

	/**
	 * Map picture.
	 *
	 * @return the string
	 */
	public String mapPicture() {
		return null;
	}

	/**
	 * Map file.
	 *
	 * @return the string
	 */
	public String mapFile() {
		return super.javaWords.FILE.getValue();
	}

	/**
	 * Map entry.
	 *
	 * @return the string
	 */
	public String mapEntry() {
		return null;
	}

}
