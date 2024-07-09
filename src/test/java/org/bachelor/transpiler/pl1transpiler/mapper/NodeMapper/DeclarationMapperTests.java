package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeclarationMapperTests {


	DeclarationMapper declarationMapper;

	SimpleNode varNode;
	SimpleNode typeNode;
	
	SimpleNode stringNode;
	SimpleNode arithmeticNode;
	SimpleNode pictureNode;
	SimpleNode fileNode;

	@BeforeEach
	void init() {
		declarationMapper = new DeclarationMapper();
		varNode = new SimpleNode(Pl1ParserTreeConstants.JJTVAR);
		
		//add Children
		typeNode = new SimpleNode(Pl1ParserTreeConstants.JJTTYPE);
		varNode.jjtAddChild(typeNode, 0);
		typeNode.jjtSetParent(varNode);
	
	}

	/**
	 * Base test for basic functionality.
	 */
	@Test
	@DisplayName("Identifier Mapping")
	void mapChildNodes_checkIdentifierMapping() {
		String[] testIdentifier = {"test_1", "0", "0", "id"};

		SimpleNode IdNode = new SimpleNode(Pl1ParserTreeConstants.JJTID);
		IdNode.jjtSetValue(testIdentifier);

		varNode.jjtAddChild(IdNode, 0);

		declarationMapper.mapChildNodes(varNode);
		assertEquals(testIdentifier[0], declarationMapper.getIdentifier());
	}

	/**
	 * More a integration test for mapType method.
	 */
	@Test
	@DisplayName("Testing String Type Mapping with checking Object and Type")
	void mapChildNodes_String() {
		String testType = "CHAR";
		String testObject = "new CHAR(5)";

		stringNode = new SimpleNode(Pl1ParserTreeConstants.JJTSTRING);
		stringNode.jjtSetValue("5");
		typeNode.jjtAddChild(stringNode, 0);
		stringNode.jjtSetParent(typeNode);

		declarationMapper.mapChildNodes(varNode);
		assertEquals(testType, declarationMapper.getType());
		assertEquals(testObject, declarationMapper.getObject());

	}
	
	/**
	 * More a integration test for mapType method.
	 */
	@Test
	@DisplayName("Testing String Type Mapping with checking Object and Type")
	void mapChildNodes_Arithmetic() {
		String testType = "double";
		
			
		arithmeticNode = new SimpleNode(Pl1ParserTreeConstants.JJTARITHMETIC);
		ArrayList<String> values = new ArrayList<String>();
		values.add("5");
		arithmeticNode.jjtSetValue(values);
		typeNode.jjtAddChild(arithmeticNode, 0);
		arithmeticNode.jjtSetParent(typeNode);

		declarationMapper.mapChildNodes(varNode);
		assertEquals(testType, declarationMapper.getType());

	}
	
	/**
	 * More a integration test for mapType method.
	 */
	@Test
	@DisplayName("Testing String Type Mapping with checking Object and Type")
	void mapChildNodes_Picture() {
		String testType = "PICTURE";
		String testObject = "new PICTURE(\"[A-Za-z]\")";
		
		pictureNode = new SimpleNode(Pl1ParserTreeConstants.JJTPICTUREEXPRESSION);
		pictureNode.jjtSetValue("X");
		typeNode.jjtAddChild(pictureNode, 0);
		pictureNode.jjtSetParent(typeNode);

		declarationMapper.mapChildNodes(varNode);
		assertEquals(testType, declarationMapper.getType());
		assertEquals(testObject, declarationMapper.getObject());

	}

	@Test
	@DisplayName("Testing File Type Mapping with checking Object and Type")
	void mapChildNodes_checkTypeMappingWithoutObject() {

		String testTypeWithoutInit = "File";
		
		fileNode = new SimpleNode(Pl1ParserTreeConstants.JJTFILE);
		typeNode.jjtAddChild(fileNode, 0);
		fileNode.jjtSetParent(typeNode);

		declarationMapper.mapChildNodes(varNode);
		assertEquals(testTypeWithoutInit, declarationMapper.getType());
	
	}

	/**
	 * -------------------------------------------------------------------------
	 * MapType Test
	 */
	

}
