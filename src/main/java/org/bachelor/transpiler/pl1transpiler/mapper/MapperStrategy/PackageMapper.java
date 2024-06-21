package org.bachelor.transpiler.pl1transpiler.mapper.MapperStrategy;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class PackageMapper extends Mapper implements ITranslationBehavior {

	private final String type = super.javaWords.PACKAGE.getValue();
	private String identifier = null;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String translate(SimpleNode simpleNode) {
		mapPackageNode(simpleNode);
		return this.type + " " + this.identifier + ";";
	}

	public void mapPackageNode(SimpleNode simpleNode) {
		Pl1ParserTreeConstants TreeSymbols = null;
		if (super.hasChildren(simpleNode)) {
			for (int i = 0; i < simpleNode.jjtGetNumChildren(); i++) {
				if (simpleNode.jjtGetChild(i).getId() == TreeSymbols.JJTID) {
					SimpleNode child = (SimpleNode)simpleNode.jjtGetChild(i);
					String[] idPropreties = (String[])child.jjtGetValue();
					this.setIdentifier(idPropreties[0]);
				}
			}
		}
	}
}
