package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.PackageMappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.DeprecatedMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.PackageMapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.scanner.InputReader;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

// TODO: Auto-generated Javadoc
/**
 * @deprecated
 * The Class ProgramMapper which inherits from Mapper. @see Mapper. This Class
 * is the Mapper for the root element of a Program. From this class all other
 * Mapper Classes will extend, it defines the dependencies needed by the main
 * Program and has the String which contains the structure of the translated
 * Java Expression. In the first process of mapping it will initiates the basic
 * project structure and create files for all boiler Plate Classes, e.g.: Binary
 * or Picutre type. Afters wards it checks the Parse-tree and then calls the
 * child class to map the child Node of the Program-Node.
 */
public class ProgramMapper extends DeprecatedMapper {

	/**
	 * The Symbol-Table is used by all child Classes to Map the equivalent
	 * Identifier for the required structure.
	 */
	public SymbolTable symbols = new SymbolTable();

	/**
	 * Contains the folder where the transpiled project should be placed.
	 *
	 * @todo should be read from Configfile
	 */
	private final static String WORKINGDIR = "./transpiled-sources/";

	/** The dependency path. */
	private String dependencyPath;

	/** The java words. */
	Template javaWords;

	/**
	 * Instantiates a new program mapper.
	 */
	public ProgramMapper(SimpleNode root) {
		init(root);
	}

	public ProgramMapper() {
	}

	/**
	 * This contains all mapped expressions from child classes. During the Mapping
	 * process this is filled up and concatinated into File afterwards.
	 */
	protected static ArrayList<String> java_expression = new ArrayList<String>();

	/**
	 * Creates Folder Structure for Base-Project. This should only be called when
	 * initializing the ProgramMapper Class from another context. Since this method
	 * will change project files and folder structures.
	 */
	private void init(SimpleNode root){
		try {
			File dependency_dir = new File(WORKINGDIR, "PLI Dependencies");
			if (dependency_dir.exists()) {
				dependencyPath = dependency_dir.toString();
			} else {
				dependency_dir.mkdir();
				dependencyPath = dependency_dir.toString();
			}

			generateDatatypeInterfaceFile();
			generatePictureClassFile();
			generateCharClassFile();
			generateBinClassFile();

		} catch (IOException io) {
			io.printStackTrace();
		}
		this.mapChildNodes(root);
	}

	/**
	 * Generate Datatype interface file. This File contains the interface which all
	 * PL/I Datatype emulation Classes like Binary or Char implement.
	 *
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public File generateDatatypeInterfaceFile() throws IOException {
		if (new File(dependencyPath, "DATATYPE_INTERFACE").exists()) {
			return null;
		}
		return createFile(dependencyPath, "DATATYPE_INTERFACE.java",
				javaWords.PACKAGE.getValue() + "\n" + javaWords.DATATYPE_INTERFACE.getValue());
	}

	/**
	 * Generate picture class file. This File will contain the Code needed to use
	 * the Picture Object which emulates the picture type from PL/I @see
	 * PictureMapper.
	 *
	 * @return the file which contains the code for the picture Class.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public File generatePictureClassFile() throws IOException {
		if (new File(dependencyPath, "PICTURE").exists()) {
			return null;
		}
		return createFile(dependencyPath, "PICTURE.java", javaWords.PICTURE_IMPORTS.getValue() + "\n"
				+ javaWords.PACKAGE.getValue() + "\n" + javaWords.PICTURE_CLASS.getValue());

	}

	/**
	 * Generate char class file. This File will contain the Code needed to use the
	 * Char Object which emulates the char type from PL/I.
	 *
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public File generateCharClassFile() throws IOException {
		if (new File(dependencyPath, "CHAR").exists()) {
			return null;
		}
		return createFile(dependencyPath, "CHAR.java",
				javaWords.PACKAGE.getValue() + "\n" + javaWords.PL1_CHAR_CLASS.getValue());
	}

	/**
	 * Generate Binary class file. This File will contain the Code needed to use the
	 * Binary Object which emulates the Binary type from PL/I.
	 *
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public File generateBinClassFile() throws IOException {
		if (new File(dependencyPath, "BINARY").exists()) {
			return null;
		}
		return createFile(dependencyPath, "BINARY.java", javaWords.BINARY_IMPORTS.getValue() + "\n"
				+ javaWords.PACKAGE.getValue() + "\n" + javaWords.BINARY_CLASS.getValue());
	}

	/**
	 * This Method will create a file specified by the given parameters.
	 *
	 * @param Path     The Path where the file should be created.
	 * @param Filename The Name of the File.
	 * @param content  The content the File should contain.
	 * 
	 * @return the file which has been generated.
	 * @throws IOException Signals that an I/O exception has occurred, when the Path
	 *                     cannot be solved.
	 */
	public File createFile(String Path, String Filename, String content) throws IOException {
		File file = new File(Path, Filename);
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.close();
		return file;
	}

	/**
	 * @param programNode
	 */
	public void mapChildNodes(SimpleNode programNode){
		if (super.hasChildren(programNode)) {
			for (int i = 0; i < programNode.jjtGetNumChildren(); i++) {
				SimpleNode packageNode = (SimpleNode) programNode.jjtGetChild(i);
				switch (programNode.jjtGetChild(i).toString()) {
				case "PACKAGE":
					PackageMapper packageMapper = new PackageMapper();
					try {
					java_expression.add(packageMapper.mapPackageNode(packageNode));
					java_expression.add(
							this.javaWords.PUBLIC.getValue() + " "
						 + this.javaWords.CLASS.getValue() + " "
						 + new InputReader().getProgramname() + " "
						 + "{ \n"
							);
					}
					catch(PackageMappingException pme) {
						pme.printStackTrace();
					}
					packageMapper.mapChildNodes(packageNode);
					break;
				default:
					break;
				}
			}
		}
	}
}
