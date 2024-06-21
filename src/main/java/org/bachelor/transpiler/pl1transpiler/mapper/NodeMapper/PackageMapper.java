package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.PackageMappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.MapperStrategy.ProgramMapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class PackageMapper extends ProgramMapper {


	public PackageMapper() {

	}

	public String mapPackageNode(SimpleNode packageNode) throws PackageMappingException{
		if(super.hasChildren(packageNode)) {
			SimpleNode child = (SimpleNode)packageNode.jjtGetChild(0);
			// {id, scope, hierachie, typ}
			String[] id = (String[] )child.jjtGetValue();
			return 
					super.javaWords.PACKAGE.getValue()
				+ " " + id[0] + ";";
		}
		else {
			throw new PackageMappingException("PL/I Package has no identifier.");
		}
	}
	
	/**
	 * @todo give variable as parameterl
	 */
	public void mapChildNodes(SimpleNode packageNode) {
		if (super.hasChildren(packageNode)) {
			for (int i = 0; i < packageNode.jjtGetNumChildren(); i++) {

				SimpleNode childNode = (SimpleNode) packageNode.jjtGetChild(i);

				switch (childNode.toString()) {
				case "VAR":					
					super.java_expression.add(new VarMapper().mapVarNode(childNode));
					break;
				case "PROC":
					super.java_expression.add(new ProcMapper().mapProcNode(childNode));
					break;
				default:
					break;
				}
			}
		}
	}

}
