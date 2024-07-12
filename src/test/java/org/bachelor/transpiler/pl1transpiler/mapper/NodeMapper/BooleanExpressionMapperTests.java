package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BooleanExpressionMapperTests {

	
	@Test
	@DisplayName("Tests Mapping of Boolean Expression")
	void setExpressionList_Test() {
		
		BooleanExpressionMapper bm = new BooleanExpressionMapper();
		String javaExpression = "( 2 < x ) == ( 3 == 2 )";
		String booleanExpression = 
								"booleanTestPackage: PACKAGE;"
							  + "booleanTestProc: PROC;"
							  + "	IF ((2 < x) = (3 = 2)) THEN;"
							 	
							  + "	END;"
							  + "END booleanTestProc;"
							  + "END booleanTestPackage;";
		ArrayList<String> expressionList = new ArrayList<String>();
		try {

			InputStream stream = new ByteArrayInputStream(booleanExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(1).jjtGetChild(0).jjtGetChild(0);
			
			bm.setExpressionList(varNode, expressionList);
			bm.mapBooleanExpression(expressionList);
			
			assertEquals(javaExpression, bm.getExpression());
			
		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	@Test
	@DisplayName("Tests Mapping of Boolean Expression")
	void setExpressionList_secondTest() {
		
		BooleanExpressionMapper bm = new BooleanExpressionMapper();
		String javaExpression = "(var_1 < var_2)";
		String booleanExpression = 
								"booleanTest1Package: PACKAGE;"
							  + "booleanTest1Proc: PROC;"
							  + "	IF (var_1 < var_2) THEN;"
							 	
							  + "	END;"
							  + "END booleanTest1Proc;"
							  + "END booleanTest1Package;";
		ArrayList<String> expressionList = new ArrayList<String>();
		try {

			InputStream stream = new ByteArrayInputStream(booleanExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(1).jjtGetChild(0).jjtGetChild(0);
			
			bm.setExpressionList(varNode, expressionList);
			bm.mapBooleanExpression(expressionList);
			
			assertEquals(javaExpression, bm.getExpression());
			
		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
}
