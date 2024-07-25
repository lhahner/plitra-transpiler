package org.bachelor.transpiler.pl1transpiler.mapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

/**
 * The Class Mapper is the Client for the Mapper Module.
 * This means if a mapping to Java of the syntaxtree is required
 * the module or class should implement this class.
 * Use the root node of a syntaxtree to translate a whole syntaxtree
 * or use just one counterpart to translate that.
 * 
 * @author Lennart Hahner
 * 
 */
public class Mapper {
	
	
	/** This contains the Java represantation of PL/I */
	public static ArrayList<String> javaExpression = new ArrayList<String>();
	
	/** This Object can set the currently used strategy */
	private TranslationMapper var = new TranslationMapper();
	
	/** Default constructor only used for testing. */
	public Mapper() {
	}

	/**
	 * Instantiates a new Mapper
	 * and invokes the iterateTree method
	 * to iterate through the Parsetree.
	 *
	 * @param root the root
	 */
	public Mapper(SimpleNode root) throws MappingException{
		this.javaExpression.clear();
		AstMapper astMapper = new AstMapper();
		this.iterateTree(root);
	}
	
	/**
	 * This method implements the Depth-First search-algorhitm recursively. 
	 * Mainly this is utilized by iterating over the whole syntaxtree one by one.
	 * For the specific Node in the @see {@link #AstMapper} class it calls a class specific to the node.
	 * The whole mapping process is done here.
	 *
	 * @param startNode The Node to begin iterating
	 * @throws NullPointerException the null pointer exception
	 */
	public void iterateTree(SimpleNode startNode) throws NullPointerException, MappingException{
		if(startNode == null) {
			throw new NullPointerException("Syntaxtree is empty, aborting...");
		}
		if (startNode.jjtGetParent() == null) {
			if (this.hasChildren(startNode)) {
				if(AstMapper.astMapper.get(startNode.getId()) != null) {
					var.setTranslationBevaior(AstMapper.astMapper.get(startNode.getId()));
					javaExpression.add(var.applyTranslate((SimpleNode)startNode));
				}
				for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
					iterateTree((SimpleNode) startNode.jjtGetChild(i));
				}
			} else {
				return;
			}
		} else {
			for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
				if(AstMapper.astMapper.get(startNode.jjtGetChild(i).getId()) != null) {
					var.setTranslationBevaior(AstMapper.astMapper.get(startNode.jjtGetChild(i).getId()));
					javaExpression.add(var.applyTranslate((SimpleNode)startNode.jjtGetChild(i)));
					
				}
				if (this.hasChildren(startNode.jjtGetChild(i))) {
					iterateTree((SimpleNode) startNode.jjtGetChild(i));
				}
			}
		}
	}

	/**
	 * Checks if a Node has children by checking the number of children provided by
	 * the SimpleNode class.
	 *
	 * @param nodeToCheck Node that should be checked for children Nodes.
	 * @return True if number of Children is larger than 0.
	 */
	public boolean hasChildren(Node nodeToCheck) {
		if (nodeToCheck.jjtGetNumChildren() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
