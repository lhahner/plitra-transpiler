package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BuiltinMapperTests {

	@Test
	@DisplayName("Mapping of mod expression in branch statement")
	void moduleMapping() {
		String epxression = 
				"testmodpackage: PACKAGE;"
				+ " testmodproc: PROC;"
				+ "		IF (MOD(2,2) = 0) THEN;"
				+ "		END;"
				+ "END testmodproc;"
				+ "END testmodpackage;";
		
		try {
			BuiltInMapper bm = new BuiltInMapper();

			InputStream stream = new ByteArrayInputStream(epxression.getBytes(StandardCharsets.UTF_8));
			Pl1Parser pl1parser = new Pl1Parser(stream);
			SimpleNode program = pl1parser.program();
			SimpleNode builtNode = (SimpleNode) program.jjtGetChild(0).jjtGetChild(1).jjtGetChild(1).jjtGetChild(0).jjtGetChild(0).jjtGetChild(0);
			
			assertEquals("2%2", bm.translate(builtNode));
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
