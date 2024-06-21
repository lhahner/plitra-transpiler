package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class BodyMapper extends ProcMapper{

	ArrayList<String > procBodyJavaExpression = new ArrayList<String >();
	ArrayList<String > callParameterList = new ArrayList<String >();
	
	public String mapBodyNode(SimpleNode bodyNode) {
		if (super.hasChildren(bodyNode)) {
			for (int i = 0; i < bodyNode.jjtGetNumChildren(); i++) {
				SimpleNode childNode = (SimpleNode) bodyNode.jjtGetChild(i);
				switch (childNode.toString()) {
				case "BRANCH":
					break;
				case "VAR":
					break;
				case "ENTRY":
					break;
				case "DISPLAY":
					break;
				case "CALLS":
					this.setCallParameterList((SimpleNode)childNode.jjtGetChild(0));
					this.procBodyJavaExpression.add(this.mapCallNode(childNode));
					break;
				case "INIT":
					break;
				case "TERMINATE":
					break;
				case "FETCH":
					break;
				case "RELEASE":
					break;
				case "LOOP":
					break;
				default:
					break;
				}
			}
			return procJavaExpression.stream().map(Object::toString).collect(Collectors.joining(""));
		}
		return null;
	}
	
	private String mapDisplayNode(SimpleNode displayNode) {
		String displayNodeAttribute = (String)displayNode.jjtGetValue();
		
		if(super.symbols.getBySymbol(displayNodeAttribute) != null){
			return 
					"System.out.println(" 
					+ displayNodeAttribute
					+ ");";
		}
		else {
			return 
					"System.out.println( \""
					+ displayNodeAttribute
					+ "\");";
		}
	}
	
	private String mapCallNode(SimpleNode callNode) {

		String parameterlist = "(";
		if (this.callParameterList.size() > 1) {
			for (String identifier : this.callParameterList) {
				if(identifier.equals(this.callParameterList.get(this.callParameterList.size()-1))) {
					parameterlist = parameterlist + " " + identifier;
					return (String)callNode.jjtGetValue() + parameterlist + ")";
				}
				parameterlist = parameterlist + " " + identifier + ",";
			}
			return (String)callNode.jjtGetValue() 
				  + parameterlist + ")";
		}
		else {
			return (String)callNode.jjtGetValue()
				+ "();";
		}
	}
	
	private void setCallParameterList(SimpleNode callChildNode) {
		this.callParameterList.add((String)callChildNode.jjtGetValue());
		if(super.hasChildren(callChildNode)) {
			setCallParameterList((SimpleNode)callChildNode.jjtGetChild(0));
		}
		return;
	}
	
	private String mapTerminateNode() {
		return "return";
	}
}
