package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriteMapperTests {

	WriteMapper writeMapper;
	SimpleNode writeStatment = new SimpleNode(Pl1ParserTreeConstants.JJTWRITE_FILE);

	String filename = "file_1";;

	@BeforeEach
	void init() {
		writeMapper = new WriteMapper();

	}

	@Test
	void mapWriteNode_expectedJavaExpression_withoutIdToWriteTo() {
		try {
			writeStatment.jjtSetValue(filename);
			assertEquals("BufferedWriter writer = new BufferedWriter(new FileWriter(" + filename + ")); \n",
					writeMapper.translate(writeStatment));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void mapWriteNode_expectedJavaExpression_withIdToWriteTo() {
		try {
			String charSequence = "str_1";
			String[] values = { filename, charSequence };
			writeStatment.jjtSetValue(values);

			assertEquals("BufferedWriter writer = new BufferedWriter(new FileWriter(" + filename
					+ ")); \n writer.write(" + charSequence + "); \n", writeMapper.translate(writeStatment));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}