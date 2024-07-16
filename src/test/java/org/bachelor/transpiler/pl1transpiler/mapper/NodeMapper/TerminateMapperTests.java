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

public class TerminateMapperTests {

	@Test
	@DisplayName("Tests Mapping of Boolean Expression")
	void mapTerminationNode_ReturnParameter() {
		
		TerminateMapper tm = new TerminateMapper();
		String termination = "return (double)terminatetestpara;";
		String value = "(double)terminatetestpara;";
		String booleanExpression = 
								"terminationTestPackage: PACKAGE;"
							  + "terminationTestProc: PROC(terminatetestpara) RETURNS(DECIMAL(4));"
							  + "	RETURN terminatetestpara"
							  + "END;"
							  + "END terminationTestProc;"
							  + "END terminationTestPackage;";
		
		try {

			InputStream stream = new ByteArrayInputStream(booleanExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode terminationNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(0).jjtGetChild(0);
			
			tm.mapTerminateNode(terminationNode);
			assertEquals(termination, tm.getTermination());
			assertEquals(value, tm.getValue());
			
		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	@Test
	@DisplayName("Tests Mapping of Boolean Expression")
	void mapTerminateNode_singleParameter() {
		
		TerminateMapper tm = new TerminateMapper();
		String termination = "return terminatetestpara1;";
		String value = "terminatetestpara;";
		String booleanExpression = 
								"terminationTest1Package: PACKAGE;"
							  + "terminationTest1Proc: PROC RETURNS(DECIMAL(4));"
							  + "	RETURN terminatetestpara1"
							  + "END;"
							  + "END terminationTest1Proc;"
							  + "END terminationTest1Package;";
		
		try {

			InputStream stream = new ByteArrayInputStream(booleanExpression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode terminationNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(0).jjtGetChild(0);
			
			tm.mapTerminateNode(terminationNode);
			assertEquals(termination, tm.getTermination());
			assertEquals(value, tm.getValue());
			
		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}
