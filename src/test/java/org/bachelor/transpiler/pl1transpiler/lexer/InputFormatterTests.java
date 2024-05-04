package org.bachelor.transpiler.pl1transpiler.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InputFormatterTests {

	InputFormatter inputformatter;
	@BeforeEach
	void init() {
		inputformatter = new InputFormatter();
	}
	
	@Test
	@DisplayName("InputFormatter")
	void formatInputFile_removeWhiteSpacesOfFile() {
	String shouldBe = 
			"DCL TEST PIC (22) "
		  + "DCL TEST1 , TEST11 PIC (1) , TEST12 PIC (2) , TEST13 PIC (3) ";
	try {
	//Cast InputStream to String to Test it
	String testData = new BufferedReader(
			new InputStreamReader(inputformatter.formatInputFile("./src/test/java/resources/pli/tests.pli")))
			.lines().collect(Collectors.joining("\n"));
	
	assertEquals(shouldBe, testData);
	
	} catch(IOException ioe) {
		System.out.println(ioe);
	}
	}
	
	void formatInputFile_throwIOException() {
		assertThrows(IOException.class, () -> {
			inputformatter.formatInputFile("./src/test/java/resources/pli/test.pli");
		});
	}
}
