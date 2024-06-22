package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.PackageMappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.scanner.InputReader;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class ProgramMapper extends Mapper implements ITranslationBehavior {
	private final String SCOPE = super.javaWords.PUBLIC.getValue();
	private final String TYPE = super.javaWords.CLASS.getValue();
	private String identifier = null;
	
	public String getSCOPE() {
		return SCOPE;
	}
	public String getTYPE() {
		return TYPE;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	
	public String translate(SimpleNode simpleNode) {
		this.mapProgramNode(simpleNode);
		return 	  this.getSCOPE() + " " 
				+ this.getTYPE() + " "
				+ this.getIdentifier()
				+ "{ \n";
	}
	/**
	 * @param programNode
	 */
	public void mapProgramNode(SimpleNode simpleNode){
		InputReader reader = new InputReader();
		this.setIdentifier(reader.getProgramname());
	}
}
