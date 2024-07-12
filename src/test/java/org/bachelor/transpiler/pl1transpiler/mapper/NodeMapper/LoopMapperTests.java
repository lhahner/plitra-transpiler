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

public class LoopMapperTests {
	
	@Test
	@DisplayName("Tests Mapping of Boolean Expression")
	void mapUntilNode_test() {
		
		BooleanExpressionMapper bm = new BooleanExpressionMapper();
		String javaExpression1 = "(var_1 < var_2)";
		String javaExpression2 = "(!var_1 = var_2)";
		String javaExpression3 = "while(" + javaExpression2 + ")";
		String untilLoop = 
								"looptestpack: PACKAGE;"
							  + "looptestproc: PROC;"
							  + "DO"
							  + "	WHILE(var_1 < var_2) UNTIL(var_1 = var_2);"
							  + "END;"
							  + "END booleanTestProc;"
							  + "END booleanTestPackage;";
		
		try {

			InputStream stream = new ByteArrayInputStream(untilLoop.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			
			SimpleNode whileBool = (SimpleNode) program.jjtGetChild(0).jjtGetChild(0).jjtGetChild(0).jjtGetChild(0);
			bm.translate(whileBool);
			assertEquals(javaExpression1, bm.getExpression());
			
			SimpleNode untilBool = (SimpleNode) program.jjtGetChild(0).jjtGetChild(0).jjtGetChild(0).jjtGetChild(1).jjtGetChild(0);
			bm.translate(untilBool);
			assertEquals(javaExpression2, bm.getExpression());
			
			
			assertEquals(javaExpression3, new EndMapper().getClosingExpression());
			
		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}
