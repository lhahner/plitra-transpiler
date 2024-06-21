package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.Collections;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class HeadMapper extends ProcMapper {

	String scope = "public ";
	String identifier;
	String type = super.javaWords.VOID.getValue();
	String parameter = "()";
	ArrayList<String> parameterList = new ArrayList<String>();

	public String mapHeadNode(SimpleNode procNode) {

		if (super.hasChildren(procNode)) {
			for (int i = 0; i < procNode.jjtGetNumChildren(); i++) {

				SimpleNode childNode = (SimpleNode) procNode.jjtGetChild(i);
				switch (childNode.toString()) {
				case "Id":
					String[] tmp = (String[]) childNode.jjtGetValue();
					this.identifier = tmp[0];
					break;
				case "PARA":
					this.setParameterList(childNode);
					this.parameter = mapParameterlist(parameterList);
					break;
				case "RETURNS":
					try {
						this.type = this.mapReturnType(childNode);
					} catch (TypeMappingException tme) {
						tme.printStackTrace();
					}
					break;
				case "OPTIONS":
					break;
				default:
					return null;
				}
			}
		} else {
			return null;
		}
		return scope + type + identifier + parameter;
	}

	public String mapParameterlist(ArrayList<String> identifiers) {
		String parameterlist = "(";
		if (identifiers.size() > 1) {
			for (String identifier : identifiers) {
				if(identifier.equals(identifiers.get(identifiers.size()-1))) {
					parameterlist = parameterlist + super.javaWords.OBJECT.getValue() + " " + identifier;
					return parameterlist + ")";
				}
				parameterlist = parameterlist + super.javaWords.OBJECT.getValue() + " " + identifier + ",";
			}
			return parameterlist + ")";
		} else {
			this.parameterList.clear();
			return "(" + super.javaWords.OBJECT.getValue() + " " + identifiers.get(0) + ")";
		}
	}

	public String mapReturnType(SimpleNode headNode) throws TypeMappingException {
		if (super.hasChildren(headNode)) {
			SimpleNode typeNode = (SimpleNode) headNode.jjtGetChild(0);
			return new TypeMapper().mapType(typeNode);
		} else {
			throw new TypeMappingException();
		}
	}
	
	public void setParameterList(SimpleNode paraNode) {
		parameterList.add((String)paraNode.jjtGetValue());
		if(super.hasChildren(paraNode)) {
			setParameterList((SimpleNode)paraNode.jjtGetChild(0));
		}
		return;
	}
}
