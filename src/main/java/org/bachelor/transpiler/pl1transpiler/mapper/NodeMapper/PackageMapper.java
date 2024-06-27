package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * <h1>Summary</h1> Will be called in the TranlationBehavior Class. During
 * Runtime the Behavior value changes to PackageMapper. This happens whenever a
 * PACKAGE Node occurs in the AST.
 * 
 * This class maps a package head expression from PL/I to Java
 * 
 * <h2>Input Example</h2> </br>
 * {@code
 * package_1 PACKAGE;
 * 
 * }
 * 
 */
public class PackageMapper extends Mapper implements ITranslationBehavior {

	/** The type of the package */
	private final String type = super.javaWords.PACKAGE.getValue();

	/** The identifier of the package */
	private String identifier = null;

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	public String getType() {
		return type;
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * This Method is part of the ITranslationBehvior interface. It will be called
	 * whenever the Behavior in class @see TranslationMapper is set to PackageMapper.
	 * Check also @see astMapper for other behavior references.
	 *
	 * @param simpleNode the package node
	 * @return the Java package for package node
	 */
	public String translate(SimpleNode simpleNode) {
		mapPackageNode(simpleNode);
		return this.getType() + " " + this.getIdentifier() + ";";
	}

	/**
	 * Map package node. This sets the identifier accordingly.
	 * @param simpleNode the simple node
	 */
	public void mapPackageNode(SimpleNode simpleNode) {
		Pl1ParserTreeConstants TreeSymbols = null;
		if (super.hasChildren(simpleNode)) {
			for (int i = 0; i < simpleNode.jjtGetNumChildren(); i++) {
				if (simpleNode.jjtGetChild(i).getId() == TreeSymbols.JJTID) {
					SimpleNode child = (SimpleNode) simpleNode.jjtGetChild(i);
					String[] idPropreties = (String[]) child.jjtGetValue();
					this.setIdentifier(idPropreties[0]);
				}
			}
		}
	}
}