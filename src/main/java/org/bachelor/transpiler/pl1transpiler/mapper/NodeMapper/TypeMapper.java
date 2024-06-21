package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class TypeMapper extends VarMapper {

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

	public String mapLocator() {
		return super.javaWords.OBJECT.getValue();
	}

	public String mapPicture() {
		return null;
	}

	public String mapFile() {
		return super.javaWords.FILE.getValue();
	}

	public String mapEntry() {
		return null;
	}

}
