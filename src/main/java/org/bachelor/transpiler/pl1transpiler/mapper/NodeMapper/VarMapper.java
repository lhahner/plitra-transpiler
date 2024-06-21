package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.SingleNodeMapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

// TODO: Auto-generated Javadoc
/**
 * @deprecated
 * The Class VarMapper.
 */
public class VarMapper extends PackageMapper implements SingleNodeMapper {

	/** The translated java expression. */
	String translatedJavaExpression = "";
	
	/** The identifier. */
	private String identifier;

	/**
	 * Map var node.
	 *
	 * @param packageNode the package node
	 * @return the string
	 */
	public String mapVarNode(SimpleNode packageNode) {
		mapChildNodes(packageNode);
		return translatedJavaExpression;
	}

	/**
	 * Map child nodes.
	 *
	 * @param packageNode the package node
	 */
	public void mapChildNodes(SimpleNode packageNode) {
		String identifier = "";
		if (super.hasChildren(packageNode)) {
			for (int i = 0; i < packageNode.jjtGetNumChildren(); i++) {
				SimpleNode childNode = (SimpleNode) packageNode.jjtGetChild(i);
				switch (childNode.toString()) {
				case "TYPE":
					try {
						translatedJavaExpression = 
								new TypeMapper().mapType(childNode)
								+ identifier
								+ "; \n";
						
					} catch (TypeMappingException tme) {
						tme.printStackTrace();
					}
					break;
				case "Id":
					String[] tmp = (String[]) childNode.jjtGetValue();
					identifier = tmp[0];
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
