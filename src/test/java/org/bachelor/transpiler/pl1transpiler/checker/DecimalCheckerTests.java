package org.bachelor.transpiler.pl1transpiler.checker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.bachelor.transpiler.pl1transpiler.checker.DecimalChecker;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DecimalCheckerTests {

	DecimalChecker checker;
	Pl1Parser pl1Parser;

	@BeforeEach
	void init() {
		checker = new DecimalChecker();

	}

	@Test
	void iterateTree_PostiveTest_identifierAddedToList() {
		try {
			File f = new File("./src/test/java/res/pli/DecimalCheckerPositveTest.pli");

			pl1Parser = new Pl1Parser(new FileInputStream(f));
			SimpleNode root = pl1Parser.program();

			checker.iterateTree(root);
			assertEquals("decChecker_1", checker.getIdentifier().get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void iterateTree_NegativeTest_ExceptionExpected() {
		File f = new File("./src/test/java/res/pli/DecimalCheckerNegativeTest.pli");

		try {
			pl1Parser = new Pl1Parser(new FileInputStream(f));
			SimpleNode root = pl1Parser.program();

			assertThrows(NumberFormatException.class, () -> {
				checker.iterateTree(root);
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
