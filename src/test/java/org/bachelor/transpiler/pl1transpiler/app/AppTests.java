package org.bachelor.transpiler.pl1transpiler.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bachelor.transpiler.pl1transpiler.app.App;
import org.bachelor.transpiler.pl1transpiler.errorhandling.IncorrectInputFileException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class AppTests 
{
	App app;
	@BeforeEach
	void init() {
		app = new App();
	}

}
