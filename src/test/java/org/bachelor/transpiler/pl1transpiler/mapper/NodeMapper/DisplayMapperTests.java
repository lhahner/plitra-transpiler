package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DisplayMapperTests {

	@Test
	@DisplayName("Read and write to stdio")
	void mapDisplayNode_testMessage() {
		DisplayMapper dm = new DisplayMapper();

		String message = "\"Hello World\"";
		String parameter = "message_para";
		String displayStatement = 
				  "displayTest: PACKAGE;"
				+ "DCL message_para CHAR(5);"
				+ "displayProc: PROC;"
				+ " DISPLAY('Hello World') REPLY (message_para);"
				+ " END displayProc;"
				+ "END displayTest;";

		try {

			InputStream stream = new ByteArrayInputStream(displayStatement.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode varNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(2).jjtGetChild(1).jjtGetChild(0);

			dm.mapDisplayNode((SimpleNode) varNode);

		} catch (Exception e) {

			e.printStackTrace();

		}
		assertEquals(message, dm.getMessage());
		assertEquals(parameter, dm.getParameter());
		
	}
}
