package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AssignMapperTest {
	// INIT(2)
	@Test
	@DisplayName("Calculation Mapping")
	void mapAssignNode_Test() {

		AssignMapper am = new AssignMapper();

		try {
			String initalisation = "initestpackage: PACKAGE;" + "	DCL testinit DECIMAL(3) INIT(2);"
					+ "END initestpackage;";

			InputStream stream = new ByteArrayInputStream(initalisation.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode initNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(0).jjtGetChild(2);

			am.mapAssignNode(initNode);

			assertEquals("2", am.getAssignment());
			assertEquals(null, am.getIdentifier());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		// var_3 = var_1 + var_2;
		@Test
		@DisplayName("Calculation Mapping")
		void mapAssignNode_CalculationTest() {

			AssignMapper am = new AssignMapper();

			try {
				String initalisation = 
						"initestpackage: PACKAGE;" 
				      +  "	 DCL testinit DECIMAL(3) INIT(2);"
					  +  "   DCL testinit_1 DECIMAL(3) INIT(2);"
				      +  "	 DCL testinit_2 DECIMAL(3);"
				      +  "	 testinit_2 = testinit + testinit_1;"
					  + "END initestpackage;";

				InputStream stream = new ByteArrayInputStream(initalisation.getBytes(StandardCharsets.UTF_8));
				Pl1Parser pl1parser = new Pl1Parser(stream);
				SimpleNode program = pl1parser.program();
				SimpleNode initNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(3);

				am.mapAssignNode(initNode);

				assertEquals("testinit + testinit_1", am.getAssignment());
				assertEquals("testinit_2", am.getIdentifier());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	
}
