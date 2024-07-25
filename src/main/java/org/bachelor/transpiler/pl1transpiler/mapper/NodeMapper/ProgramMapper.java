package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.scanner.InputReader;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

/**
 * This class is used to translate an BooleaExpression Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * The Filename will be the class of the Program. For exmaple filename file_1
 * 
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * <br>
 * public class file_1 { </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class ProgramMapper implements ITranslationBehavior {
	
	/** The scope. */
	private final String SCOPE = Template.PUBLIC.getValue();
	
	/** The type. */
	private final String TYPE = Template.CLASS.getValue();
	
	/** The identifier. */
	private String identifier = null;
	
	/**
	 * Gets the scope.
	 *
	 * @return the scope
	 */
	public String getSCOPE() {
		return SCOPE;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getTYPE() {
		return TYPE;
	}
	
	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
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
	 * Translate.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 */
	public String translate(SimpleNode simpleNode) throws MappingException{
		this.mapProgramNode(simpleNode);
		if (this.getIdentifier() != null)
			return 	  this.getSCOPE() + " " 
					+ this.getTYPE() + " "
					+ this.getIdentifier()
					+ "{ \n";
		else 
			throw new MappingException("Identifier not definied for Program" + simpleNode.toString() + " in " + this.getClass().toString());
	}
	
	/**
	 * Maps the program Node of the syntaxtree.
	 *
	 * @param simpleNode the simple node
	 */
	public void mapProgramNode(SimpleNode simpleNode){
		InputReader reader = new InputReader();
		this.setIdentifier(reader.getProgramname());
	}
}
