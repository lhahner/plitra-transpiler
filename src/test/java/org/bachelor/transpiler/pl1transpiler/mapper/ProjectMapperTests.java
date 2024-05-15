package org.bachelor.transpiler.pl1transpiler.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProjectMapperTests {
	
	ProjectMapper projectmapper;
	
	@Test
	@DisplayName("Test Projectmapper init")
	void init_shouldCreateWorkingDir() {
		projectmapper = new ProjectMapper();
		File shouldBeCreated =  new File("./target/transpiled-sources/Transpiler Dependencies/");
		assertTrue(shouldBeCreated.exists());
	}
}
