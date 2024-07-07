package org.bachelor.transpiler.pl1transpiler.scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The Class InputReader.
 */
public class InputReader {

	/** Contains the Program-Name which is specified by the Filename. */
	public static String program = "";

	/**
	 * Gets the input file.
	 *
	 * @param inputFile the input file
	 * @return the input file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public FileInputStream getInputFile(String inputFile) throws IOException {
		final File pl1File = new File(inputFile);
		FileInputStream inputStream = new FileInputStream(pl1File);
		return inputStream;
	}

	/**
	 * Reads the configuration file from the location inside the project.
	 *
	 * @return The Configuration as type File.
	 */
	public File readConfigFile() {
		File config = new File("/res/config/config");
		return config;
	}

	/**
	 * Gets the input file path.
	 *
	 * @param config the config
	 * @return the input file path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getInputFilePath(File config) throws IOException {
		String inputFilePath = "";

		FileInputStream config_fis = new FileInputStream(config);
		Scanner config_sc = new Scanner(config_fis);
		while (config_sc.hasNext()) {
			String line = config_sc.next();
			if (line.substring(0, 4).equals("PATH")) {
				inputFilePath = line.substring(5, line.indexOf(';'));
				config_sc.close();
				printProgramname(inputFilePath);
				return inputFilePath;
			} else {
				config_sc.close();
				throw new IOException("PATH not definied in Config file");
			}
		}
		config_sc.close();
		return inputFilePath;
	}

	/**
	 * Gets the programname.
	 *
	 * @return the programname
	 */
	public String getProgramname() {
		return this.program;
	}

	/**
	 * Prints the programname.
	 *
	 * @param path the path
	 */
	public void printProgramname(String path) {
		this.program = path.substring(path.lastIndexOf('/') + 1, path.indexOf(".pli"));
	}
}