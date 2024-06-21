package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.SingleNodeMapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class VarMapper extends PackageMapper implements SingleNodeMapper {

	String translatedJavaExpression = "";
	
	private String identifier;

	public String mapVarNode(SimpleNode packageNode) {
		mapChildNodes(packageNode);
		return translatedJavaExpression;
	}

	/**
	 * @param programNode
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

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
