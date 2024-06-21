package org.bachelor.transpiler.pl1transpiler.mapper.MapperStrategy;

import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

public class Mapper {
	protected Template javaWords;
	TranslationMapper var = new TranslationMapper();
	

	public Mapper(SimpleNode root) {
		AstMapper astMapper = new AstMapper();
		this.iterateTree(root);
	}
	
	public Mapper() {
	}

	public void iterateTree(SimpleNode startNode) {
		if (startNode.jjtGetParent() == null) {
			if (this.hasChildren(startNode)) {
				var.setTranslationBevaior(AstMapper.astMapper.get(startNode.getId()));
				var.applyTranslate((SimpleNode)startNode);
				
				for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
					iterateTree((SimpleNode) startNode.jjtGetChild(i));
				}
			} else {
				return;
			}
		} else {
			for (int i = 0; i < startNode.jjtGetNumChildren(); i++) {
				var.setTranslationBevaior(AstMapper.astMapper.get(startNode.getId()));
				var.applyTranslate(startNode);
				
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
