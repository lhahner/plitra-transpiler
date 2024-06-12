package org.bachelor.transpiler.pl1transpiler.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProjectMapperTests {
	
	ProjectMapper projectmapper;
	
	@Test
	@DisplayName("Test Projectmapper init")
	void init_shouldCreateWorkingDir() throws IOException{
		projectmapper = new ProjectMapper();
		File shouldBeCreated =  new File("./transpiled-sources/PLI Dependencies/");
		File datatypeFile = new File("./transpiled-sources/PLI Dependencies/DATATYPE_INTERFACE.java");
		File picFile = new File("./transpiled-sources/PLI Dependencies/PICTURE.java");
		File charFile = new File("./transpiled-sources/PLI Dependencies/CHAR.java");
		File binFile = new File("./transpiled-sources/PLI Dependencies/BINARY.java");
	
		assertTrue(shouldBeCreated.exists());
		assertTrue(datatypeFile.exists());
		assertTrue(picFile.exists());
		assertTrue(charFile.exists());
		assertTrue(binFile.exists());
	}
}
