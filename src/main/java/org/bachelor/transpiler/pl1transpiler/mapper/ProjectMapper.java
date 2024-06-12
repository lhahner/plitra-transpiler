package org.bachelor.transpiler.pl1transpiler.mapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class ProjectMapper implements Mapper{
	/**
	 * @todo should be read from Configfile
	 */
	private final static String WORKINGDIR = "./transpiled-sources/";
	private String dependencyPath;
	Template javaWords;
	
	public ProjectMapper() {
		init();
	}
	/**
	 * Creates Folder Structure for Base-Project
	 */
	public void init() {
		try{	
			File dependency_dir = new File(WORKINGDIR, "PLI Dependencies");
			if(dependency_dir.exists()) {
				dependencyPath = dependency_dir.toString();
			}
			else {
				dependency_dir.mkdir();
				dependencyPath = dependency_dir.toString();
			}
			
			generateDatatypeInterfaceFile();
			generatePictureClassFile();
			generateCharClassFile();
			generateBinClassFile();
			
		}catch(IOException io) {
			io.printStackTrace();
		}
	}
	
	public File generateDatatypeInterfaceFile() throws IOException {
		if(new File(dependencyPath, "DATATYPE_INTERFACE").exists()) {
			return null;
		}
		return createFile(
				dependencyPath,
				"DATATYPE_INTERFACE.java", 
				javaWords.PACKAGE.getValue() + "\n" + javaWords.DATATYPE_INTERFACE.getValue());
	}
	public File generatePictureClassFile() throws IOException {
		if(new File(dependencyPath, "PICTURE").exists()) {
			return null;
		}
		return createFile(
				dependencyPath,
				"PICTURE.java",
				javaWords.PICTURE_IMPORTS.getValue() + "\n" + 
				javaWords.PACKAGE.getValue() + "\n" + javaWords.PICTURE_CLASS.getValue());
		
	}
	public File generateCharClassFile() throws IOException {
		if(new File(dependencyPath, "CHAR").exists()) {
			return null;
		}
		return createFile(
				dependencyPath,
				"CHAR.java",
				javaWords.PACKAGE.getValue() + "\n" +javaWords.PL1_CHAR_CLASS.getValue());
	}
	public File generateBinClassFile() throws IOException {
		if(new File(dependencyPath, "BINARY").exists()) {
			return null;
		}
		return createFile(
				dependencyPath,
				"BINARY.java",
				javaWords.BINARY_IMPORTS.getValue() + "\n" +
 				javaWords.PACKAGE.getValue() + "\n" +javaWords.BINARY_CLASS.getValue());
	}
	public File createFile(String Path, String Filename, String content) throws IOException {
		File file = new File(Path, Filename);
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.close();
		return file;
	}
}
