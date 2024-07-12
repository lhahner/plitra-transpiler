package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeadMapperTests {

	@Test
	@DisplayName("Map Head Node")
	void mapHeadNode_Identifier() {
		HeadMapper headm = new HeadMapper();
		
		String identifier = "A10_Revenue";
		String paraList = "(Object rev_1)";
		String type  = "@Char(5) double";
		
		String javaExpression = "public void proc_1()";
		String decimalExpression = "testproc_1_package: PACKAGE;" 
								 + "	A10_Revenue: PROC(rev_1) RETURNS(DECIMAL(5)) OPTIONS(INLINE);"
								 + "	END proc_1;" 
								 + "END testproc_1_package;";

		try {

			InputStream stream = new ByteArrayInputStream(decimalExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1);

			headm.mapHeadNode((SimpleNode)varNode.jjtGetChild(0));
			
		} catch (Exception e) {

			e.printStackTrace();

		}
		assertEquals(identifier, headm.getIdentifier());
		assertEquals(paraList, headm.getParameter());
		assertEquals(type, headm.getType());
	}

}
