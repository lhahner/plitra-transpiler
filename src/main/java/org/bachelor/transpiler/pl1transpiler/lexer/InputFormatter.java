package org.bachelor.transpiler.pl1transpiler.lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class InputFormatter {
	public FileInputStream formatInputFile(String inputFile) throws IOException{
	 try {
		final File pl1File = new File(inputFile);
		final File throughputFile = new File("./src/main/java/res/var/code_tp.pli");
		Lexer lex = new Lexer();
		String content = "";
		String token = "";
		FileInputStream inputStream = new FileInputStream(pl1File);
		FileWriter writer = new FileWriter(throughputFile);
		Scanner sc = new Scanner(inputStream);
		while (sc.hasNext()) {
			content = content + lex.getToken(sc.next()) + " ";
		}
		writer.write(content + " ");
		sc.close();
		writer.close();
		FileInputStream parseInputStream = new FileInputStream(throughputFile);
		return parseInputStream;
	 }
	 catch(IOException ioe) {
		throw new IOException(ioe);
	}
 }
}