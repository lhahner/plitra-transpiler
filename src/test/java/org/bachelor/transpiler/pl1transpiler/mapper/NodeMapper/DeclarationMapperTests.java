package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.LexicalErrorException;
import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeclarationMapperTests {

	//TODO Input PL/I Code -> Java Code
	
	DeclarationMapper declarationMapper;

	SimpleNode varNode;
	SimpleNode typeNode;
	
	SimpleNode stringNode = new SimpleNode(Pl1ParserTreeConstants.JJTSTRING);
	SimpleNode arithmeticNode = new SimpleNode(Pl1ParserTreeConstants.JJTARITHMETIC);
	SimpleNode pictureNode = new SimpleNode(Pl1ParserTreeConstants.JJTPICTUREEXPRESSION);
	SimpleNode fileNode = new SimpleNode(Pl1ParserTreeConstants.JJTFILE);;

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
		IdNode.jjtSetParent(varNode);
		varNode.jjtAddChild(IdNode, 0);
		
		
		try {
			declarationMapper.mapChildNodes(varNode);
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(testIdentifier[0], declarationMapper.getIdentifier());
	}
	
	@Test
	@DisplayName("Testing the behaviour if a specific type is set")
	void mapType_String() {
		typeNode.jjtAddChild(stringNode, 0);
		stringNode.jjtSetParent(typeNode);
		stringNode.jjtSetValue("5");
		
		String expectedType = "@Char(5) String";
		
		try {
			declarationMapper.mapType(typeNode);
			assertEquals(expectedType, declarationMapper.getType());
			assertEquals(";", declarationMapper.mapType(typeNode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Testing the behaviour if a specific type is set")
	void mapType_Picture() {
		typeNode.jjtAddChild(pictureNode, 0);
		pictureNode.jjtSetParent(typeNode);
		pictureNode.jjtSetValue("9V99");
		
		String expectedType = "PICTURE";
		
		try {
			declarationMapper.mapType(typeNode);
			assertEquals(expectedType, declarationMapper.getType());
			assertEquals(" = new PICTURE(\"[0-9][\\.\\*][0-9][0-9]\");", declarationMapper.mapType(typeNode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Testing the behaviour if a specific type is set")
	void mapType_File() {
		typeNode.jjtAddChild(fileNode, 0);
		fileNode.jjtSetParent(typeNode);
		
		String expectedType = "File";
		
		try {
			declarationMapper.mapType(typeNode);
			assertEquals(expectedType, declarationMapper.getType());
			assertEquals(" = new File();", declarationMapper.mapType(typeNode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Testing the behaviour if a specific type is set")
	void mapArithmetic() {
		typeNode.jjtAddChild(arithmeticNode, 0);
		arithmeticNode.jjtSetParent(typeNode);
		
		ArrayList<String> options = new ArrayList<String>();
		
		try {
			options.add("COMPLEX");
			options.add("5");
			arithmeticNode.jjtSetValue(options);
			
			declarationMapper.mapArithmetic(arithmeticNode);
			assertEquals("COMPLEX", declarationMapper.getType());
			assertEquals(" = new COMPLEX(5);", declarationMapper.mapArithmetic(arithmeticNode));
			options.clear();
			
			options.add("BINARY");
			options.add("5");
			arithmeticNode.jjtSetValue(options);
			
			declarationMapper.mapArithmetic(arithmeticNode);
			assertEquals("BINARY", declarationMapper.getType());
			assertEquals(" = new BINARY(5);", declarationMapper.mapArithmetic(arithmeticNode));
			options.clear();
			
			options.add("REAL");
			options.add("5");
			arithmeticNode.jjtSetValue(options);
			
			declarationMapper.mapArithmetic(arithmeticNode);
			assertEquals("@Decimal(5) double", declarationMapper.getType());
			assertEquals(";", declarationMapper.mapArithmetic(arithmeticNode));
			options.clear();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
