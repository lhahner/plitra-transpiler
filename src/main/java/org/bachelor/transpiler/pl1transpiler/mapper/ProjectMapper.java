package org.bachelor.transpiler.pl1transpiler.mapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class ProjectMapper implements Mapper{
	/**
	 * @todo should be read from Configfile
	 */
	private final static String WORKINGDIR = "./target/transpiled-sources/";
	Template javaWords;

	
	public ProjectMapper() {
		init();
	}
	/**
	 * Creates Folder Structure for Base-Project
	 */
	public void init() {
		File dependency_dir = new File(WORKINGDIR, "Transpiler Dependencies");
		if(dependency_dir.exists()) {
			return;
		}
		else {
			dependency_dir.mkdir();
		}
	}
	public File getDatatypeInterface() throws IOException {
		return createFile("datatype", 
				javaWords.PACKAGE.getValue() + "\n" + javaWords.DATATYPE_INTERFACE.getValue());
	}
	public File getPictureClass() throws IOException {
		return createFile("PICTURE_CLASS", javaWords.PACKAGE.getValue() + "\n" + javaWords.PICTURE_CLASS.getValue());
		
	}
	public File getCharClass() throws IOException {
		return createFile("PL1_CHAR_CLASS", javaWords.PACKAGE.getValue() + "\n" +javaWords.PL1_CHAR_CLASS.getValue());
	}
	public File getBinClass() throws IOException {
		return createFile("BINARY_CLASS", javaWords.PACKAGE.getValue() + "\n" +javaWords.BINARY_CLASS.getValue());
	}
	public File createFile(String Filename, String content) throws IOException {
		File file = new File(Filename);
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		return file;
	}
}
