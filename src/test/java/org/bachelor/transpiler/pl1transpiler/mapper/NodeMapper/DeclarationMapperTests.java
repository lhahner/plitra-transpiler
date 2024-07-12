package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.LexicalErrorException;
import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.errorhandling.TypeMappingException;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeclarationMapperTests {

	// PROGRAM -> PACKAGE -> VAR -> END

	/**
	 * Base test for basic functionality.
	 */
	@Test
	@DisplayName("Identifier &  Decimal Mapping")
	void mapChildNodes_checkIdentifierMapping() {
		DeclarationMapper declarationMapper = new DeclarationMapper();
		String identifier = "var_3";
		String javaExpression = "@Decimal(5) double";
		String decimalExpression = "test_1_package: PACKAGE;" + "	DCL var_3 FIXED DECIMAL(5)" + "END test_1_package;";

		try {

			InputStream stream = new ByteArrayInputStream(decimalExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1);

			declarationMapper.mapChildNodes(varNode);
			declarationMapper.mapArithmetic((SimpleNode)varNode.jjtGetChild(1).jjtGetChild(0));

		} catch (Exception e) {

			e.printStackTrace();

		}
		assertEquals(identifier, declarationMapper.getIdentifier());
		assertEquals(javaExpression, declarationMapper.getType());
	}
	
	@Test
	@DisplayName("Negative Test")
	void mapChildNodes_NegativeTest() {
		DeclarationMapper declarationMapper = new DeclarationMapper();
		String decimalExpression = "test_1_package: PACKAGE;" 
								 + "	DCL var_3 CHAR(5)" 
								 + "END test_1_package;";

		try {

			InputStream stream = new ByteArrayInputStream(decimalExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1);
			
			assertThrows(TypeMappingException.class, () -> {
				declarationMapper.mapArithmetic((SimpleNode)varNode.jjtGetChild(1).jjtGetChild(0));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Base test for basic functionality.
	 */
	@Test
	@DisplayName("String Mapping")
	void mapTye_String() {
		String javaExpression = "@Char(3) String";
		String charExpression = "test_2_package: PACKAGE;" + "	DCL Delims CHAR(3);" + "END test_2_package;";

		try {

			InputStream stream = new ByteArrayInputStream(charExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(1);

			DeclarationMapper dm = new DeclarationMapper();
			dm.mapType(varNode);
			assertEquals(javaExpression, dm.getType());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	@Test
	@DisplayName("Picture Type Mapping")
	void mapTye_Picture() {
		String javaExpressionType = "PICTURE";
		String javaExpressionObject = " = new PICTURE(\"[0-9][\\.\\*][0-9][0-9][A-Za-z ]\");";
		String charExpression = 
				"test_3_package: PACKAGE;" 
				+ "	DCL Delims PIC '9V99A';" 
				+ "END test_3_package;";

		try {

			InputStream stream = new ByteArrayInputStream(charExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(1);

			DeclarationMapper dm = new DeclarationMapper();
			assertEquals(javaExpressionObject, dm.mapType(varNode));
			assertEquals(javaExpressionType, dm.getType());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	@Test
	@DisplayName("File Type Mapping")
	void mapTye_File() {
		String javaExpressionType = "File";
		String javaExpressionObject = " = new File();";
		String charExpression = 
				"test_4_package: PACKAGE;" 
				+ "	dcl Payroll file;" 
				+ "END test_4_package;";

		try {

			InputStream stream = new ByteArrayInputStream(charExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(1);

			DeclarationMapper dm = new DeclarationMapper();
			assertEquals(javaExpressionObject, dm.mapType(varNode));
			assertEquals(javaExpressionType, dm.getType());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	@Test
	@DisplayName("Complex Type Mapping")
	void mapTye_Complex() {
		String javaExpressionType = "COMPLEX";
		String javaExpressionObject = " = new COMPLEX(5);";
		String charExpression = 
				"test_5_package: PACKAGE;" 
				+ "	DCL var_5 COMPLEX DECIMAL(5);" 
				+ "END test_5_package;";

		try {

			InputStream stream = new ByteArrayInputStream(charExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(1);

			DeclarationMapper dm = new DeclarationMapper();
			assertEquals(javaExpressionObject, dm.mapType(varNode));
			assertEquals(javaExpressionType, dm.getType());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
