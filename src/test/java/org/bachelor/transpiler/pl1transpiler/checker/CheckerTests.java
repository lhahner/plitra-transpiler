package org.bachelor.transpiler.pl1transpiler.checker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.checker.DecimalChecker;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.DeclarationMapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckerTests {

	Checker checker;
	Pl1Parser pl1Parser;

	SimpleNode varNode;
	
	SimpleNode IdNode;
	SimpleNode typeNode;

	SimpleNode stringNode;
	SimpleNode arithmeticNode;
	SimpleNode pictureNode;
	SimpleNode fileNode;

	@BeforeEach
	void init() {
		varNode = new SimpleNode(Pl1ParserTreeConstants.JJTVAR);

		IdNode = new SimpleNode(Pl1ParserTreeConstants.JJTID);
		
		String[] var_1 = {"var_1", "1", "1", "id"};
		
		IdNode.jjtSetValue(var_1);
		varNode.jjtAddChild(IdNode, 0);
		IdNode.jjtSetParent(varNode);

		// add Children
		typeNode = new SimpleNode(Pl1ParserTreeConstants.JJTTYPE);
		varNode.jjtAddChild(typeNode, 1);
		typeNode.jjtSetParent(varNode);

		checker = new Checker();
	}

	@Test
	void checkDecimalDeclaration_assignedIdentifiers() {
		try {
			arithmeticNode = new SimpleNode(Pl1ParserTreeConstants.JJTARITHMETIC);
			typeNode.jjtAddChild(arithmeticNode, 0);
			arithmeticNode.jjtSetParent(typeNode);
			
			checker.checkDecimalDeclaration(arithmeticNode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void checkDecimalDeclaration_assignedAssignement() {
		//TODO
	}

}
