package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalcMapperTests {
	// var_3 = var_1 + var_2
	@Test
	@DisplayName("Calculation Mapping")
	void setTerms_Test() {
		CalcMapper cm = new CalcMapper();
		String calculation = "var_1 + var_2";

		SimpleNode sn = new SimpleNode(Pl1ParserTreeConstants.JJTCALC);
		sn.jjtSetValue(calculation);

		cm.setTerms(sn);

		assertEquals("var_1 + var_2", cm.getTerms().get(0));
	}
}
