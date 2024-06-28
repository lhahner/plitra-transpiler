package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeclarationMapperTests {

	DeclarationMapper declarationMapper;
	Pl1ParserTreeConstants treeSymbols = null;
	SimpleNode varNode;

	@BeforeEach
	void init() {
		declarationMapper = new DeclarationMapper();
		varNode = new SimpleNode(treeSymbols.JJTVAR);
	}

	/**
	 * Base test for basic functionality.
	 */
	@Test
	@DisplayName("Identifier Mapping")
	void mapChildNodes_checkIdentifierMapping() {
		String testIdentifier = "test_1";

		SimpleNode IdNode = new SimpleNode(treeSymbols.JJTID);
		IdNode.jjtSetValue(testIdentifier);

		varNode.jjtAddChild(IdNode, 0);

		declarationMapper.mapChildNodes(varNode);
		assertEquals(testIdentifier, declarationMapper.getIdentifier());
	}

	/**
	 * More a integration test for mapType method.
	 */
	@Test
	@DisplayName("Testing String Type Mapping with checking Object and Type")
	void mapChildNodes_checkTypeMappingWithObject() {
		String testType = "CHAR";
		String testObject = "new CHAR";

		SimpleNode typeNode = new SimpleNode(treeSymbols.JJTTYPE);
		varNode.jjtAddChild(typeNode, 0);

		typeNode.jjtAddChild(new SimpleNode(treeSymbols.JJTSTRING), 0);

		declarationMapper.mapChildNodes(varNode);
		assertEquals(testType, declarationMapper.getType());
		assertEquals(testObject, declarationMapper.getType());

	}

	/**
	 * More a integration test for mapType method.
	 */
	@Test
	@DisplayName("Testing File Type Mapping with checking Object and Type")
	void mapChildNodes_checkTypeMappingWithoutObject() {

		String testTypeWithoutInit = "FILE";

		SimpleNode typeNode = new SimpleNode(treeSymbols.JJTTYPE);
		varNode.jjtAddChild(typeNode, 0);

		typeNode.jjtAddChild(new SimpleNode(treeSymbols.JJTFILE), 0);

		declarationMapper.mapChildNodes(varNode);
		assertEquals(testTypeWithoutInit, declarationMapper.getType());
		assertEquals(null, declarationMapper.getType());
	}

	/**
	 * More a integration test for mapType method.
	 */
	@Test
	@DisplayName("Positive testing Type Mapping")
	void mapType_checkTypeMapping_positve() {
		SimpleNode[] allTypes = { new SimpleNode(treeSymbols.JJTARITHMETIC), new SimpleNode(treeSymbols.JJTSTRING),
				new SimpleNode(treeSymbols.JJTPICTUREEXPRESSION), new SimpleNode(treeSymbols.JJTFILE) };

		String[] shouldBe = { "DECIMAL", "CHAR", "PICTURE", "FILE" };

		for (int i = 0; i < allTypes.length; i++) {
			try {
				declarationMapper.mapType(allTypes[i]);
				assertEquals(declarationMapper.getType(), allTypes[i]);
			} catch (TypeMappingException tme) {
				tme.printStackTrace();
			}
		}
	}

	/**
	 * More a integration test for mapType method.
	 */
	@Test
	@DisplayName("Negative testing Type Mapping")
	void mapType_checkTypeMapping_negative() {
		SimpleNode notKnownType = new SimpleNode(treeSymbols.JJTBOOL);

		assertThrows(TypeMappingException.class, () -> {
			declarationMapper.mapType(notKnownType);
		});
	}

	@Test
	@DisplayName("Positive testing Arithmetic")
	void mapArithmetic_areAllTypesMapped() {

	}

}
