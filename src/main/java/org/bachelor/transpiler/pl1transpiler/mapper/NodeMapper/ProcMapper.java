package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class ProcMapper extends PackageMapper{
	ArrayList<String > procJavaExpression = new ArrayList<String >();
	
	public String mapProcNode(SimpleNode packageNode) {

		if (super.hasChildren(packageNode)) {
			for (int i = 0; i < packageNode.jjtGetNumChildren(); i++) {
				SimpleNode childNode = (SimpleNode) packageNode.jjtGetChild(i);
				switch (childNode.toString()) {
				case "HEAD":
					procJavaExpression.add(
							new HeadMapper().mapHeadNode(childNode) + " {"
					);
					break;
				case "BODY":
					procJavaExpression.add(" }");
					break;
				default:
					break;
				}
			}
			return procJavaExpression.stream().map(Object::toString).collect(Collectors.joining(""));
		}
		return null;
	}
}
