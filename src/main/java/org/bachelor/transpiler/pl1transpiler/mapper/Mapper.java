package org.bachelor.transpiler.pl1transpiler.mapper;

import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

// TODO: Auto-generated Javadoc
/**
 * The Class Mapper.
 */
public class Mapper {
	
	
	/** PL/I Symboltable */
	protected static SymbolTable symbols = SymbolTable.getInstance();
	
	/** The var. */
	TranslationMapper var = new TranslationMapper();
	
	/** Parse Node constants */
	protected static Pl1ParserTreeConstants  treeSymbols = null;
	
	/**
	 * Instantiates a new mapper.
	 */
	public Mapper() {
	}

	/**
	 * Instantiates a new mapper.
	 *
	 * @param root the root
	 */
	public Mapper(SimpleNode root) {
		AstMapper astMapper = new AstMapper();
		this.iterateTree(root);
	}
	
	/**
	 * This Method implements the Depth-In-First Search Algorhitmus recursively. 
	 * Mainly this is utilized by iterating over the whole parse-tree one by one.
	 * For the specific Node in the AstMapper class it calls a class specific to the node.
	 *
	 * @param startNode The Node to begin iterating
	 */
	public void iterateTree(SimpleNode startNode) throws NullPointerException{
		if (startNode.jjtGetParent() == null) {
			if (this.hasChildren(startNode)) {
				if(AstMapper.astMapper.get(startNode.getId()) != null) {
					var.setTranslationBevaior(AstMapper.astMapper.get(startNode.getId()));
					var.applyTranslate((SimpleNode)startNode);
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
					var.applyTranslate((SimpleNode)startNode.jjtGetChild(i));
					
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
