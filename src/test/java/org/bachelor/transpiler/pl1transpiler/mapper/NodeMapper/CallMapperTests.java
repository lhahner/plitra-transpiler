package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CallMapperTests {
	@Test
	@DisplayName("Test Call Statement")
	void mapCallStatement() {
		String identifier = "proc_1";
		String parameter = "(\"test_1\",1)";
		String charExpression = 
				    "testcall_package: PACKAGE;" 
					+ "proc_3: PROC;"
				    + "	CALL proc_1('test_1', 1);" 
				    + "END proc_3;" 
					+ "END testcall_package;";

		try {

			InputStream stream = new ByteArrayInputStream(charExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(1).jjtGetChild(0);

			CallMapper cm = new CallMapper();
			cm.mapCallStatement(varNode);
			assertEquals(identifier, cm.getIdentifier());
			assertEquals(parameter, cm.getParameter());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	@Test
	@DisplayName("Test Call Statement")
	void mapCallStatement_withoutParameter() {
		String identifier = "proc_2";
		String parameter = "()";
		String charExpression = 
				    "testcall1_package: PACKAGE;" 
					+ "proc_4: PROC;"
				    + "	CALL proc_2;" 
				    + "END proc_4;" 
					+ "END testcall1_package;";

		try {

			InputStream stream = new ByteArrayInputStream(charExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(1).jjtGetChild(0);

			CallMapper cm = new CallMapper();
			cm.mapCallStatement(varNode);
			assertEquals(identifier, cm.getIdentifier());
			assertEquals(parameter, cm.getParameter());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}
