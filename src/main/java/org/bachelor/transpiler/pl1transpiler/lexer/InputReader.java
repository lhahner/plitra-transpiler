package org.bachelor.transpiler.pl1transpiler.lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class InputReader {
	public FileInputStream getInputFile(String inputFile) throws IOException{
	 try {
		final File pl1File = new File(inputFile);
		FileInputStream inputStream = new FileInputStream(pl1File);
		return inputStream;
	 }
	 catch(IOException ioe) {
		throw new IOException(ioe);
	}
 }
}