package org.bachelor.transpiler.pl1transpiler.lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
	public File readConfigFile() {
		File config = new File("/res/config/config");
		return config;
	}
	
	public String getInputFilePath(File config) throws IOException {
	    String inputFilePath = "";
		
		FileInputStream config_fis = new FileInputStream(config);
		Scanner config_sc = new Scanner(config_fis);
		while(config_sc.hasNext()) {
			String line = config_sc.next();
			if(line.substring(0, 4).equals("PATH")) {
				inputFilePath = line.substring(5, line.indexOf(';'));
				config_sc.close();
				return inputFilePath;
			}
			else {
				config_sc.close();
				throw new IOException("PATH not definied");
			}
		}
		config_sc.close();
		return inputFilePath;
	}
}