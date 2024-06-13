package org.bachelor.transpiler.pl1transpiler.checker;

import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TraverseParseTreeTests {
	HierachieChecker traverser;
	SimpleNode root;
	
	SimpleNode child1;
	SimpleNode child2;
	SimpleNode child1_of_child1;
	SimpleNode child2_of_child1;
	
	@BeforeEach
	void init() {
		traverser = new HierachieChecker();
		//Setup of the tree.
//		root.jjtSetValue("root");
//		
//		child1.jjtSetParent(root);
//		child1.jjtSetValue("child1");
//		
//		child2.jjtSetParent(root);
//		child2.jjtSetValue("child2");
//		
//		child1_of_child1.jjtSetParent(child1);
//		child1_of_child1.jjtSetValue("child1_of_child1");
//		
//		child2_of_child1.jjtSetParent(child1);
//		child2_of_child1.jjtSetValue("child2_of_child1");
	}
	
	@Test
	@DisplayName("Check Last Stack entrance")
	void readDepthFirst() {
		
	}
}
