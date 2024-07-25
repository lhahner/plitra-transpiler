package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

/**
 * This class is used to translate an BooleaExpression Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * READ FILE(file_1) INTO(var_1)<br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * Scanner scanner = new Scanner(file_1);<br>
 * </code>
 * <br>
 * 
 * @author Lennart Hahner
 */
public class ReadFileMapper implements ITranslationBehavior {
	
	/** The file identifier. */
	public String fileIdentifier;
	
	/**
	 * Gets the file identifier.
	 *
	 * @return the file identifier
	 */
	public String getFileIdentifier() {
		return fileIdentifier;
	}

	/**
	 * Sets the file identifier.
	 *
	 * @param fileIdentifier the new file identifier
	 */
	public void setFileIdentifier(String fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}

	/**
	 * Translate.
	 *
	 * @param simpleNode the simple node
	 * @return the string
	 */
	public String translate(SimpleNode simpleNode) {
		this.setFileIdentifier((String)simpleNode.jjtGetValue());
		
		return simpleNode.jjtGetValue() != null ? 
				Template.SCANNER.getValue() + "(" + this.getFileIdentifier() + ")"
			  : ""	;
	}
}
