package org.bachelor.transpiler.pl1transpiler.mapper;

import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MapperTests {
	
	Mapper mapper;
	
	@BeforeEach
	void init() {
		mapper = new Mapper();
	}
	
	//Integration Test of all Mapper Classes
	@Test
	@DisplayName("Iterate Tree")
	public void iterateTree_TestForFailure() {
		SimpleNode testSimpleNode = new SimpleNode(0);
	}
}
