package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

public class Checker {
	
	//Saves value found by searchValue
	public SimpleNode foundValue;
	
	//Saves all Nodes found by searchNode
	public ArrayList<SimpleNode> foundNodes = new ArrayList<SimpleNode>();
	
	/**
	 * Stack of type String.
	 */
	Stack<String> stack = new Stack<String>(256);
	SymbolTable st = new SymbolTable();
	
	/**
	 * Method Searches parse tree for value & node, saves node globally in found.
	 * @param root Node of parse-tree.
	 * @param String value to search for in parse tree.
	 */
	protected void searchValue(SimpleNode root, String value, String expectedNode) {
		// Special case its root of tree
		if (root.jjtGetParent() == null) {
			// Push root to stack
			stack.push(root.toString());
			stack.printStack();
			if(isNodeValue((SimpleNode)root, value, expectedNode)) {
				this.foundValue = root;
			}
			
			// Maybe root has children
			if (this.hasChildren(root)) {
				for (int i = 0; i < root.jjtGetNumChildren(); i++) {
					stack.push(root.jjtGetChild(i).toString());
					stack.printStack();
					
					if(isNodeValue((SimpleNode)root.jjtGetChild(i), value, expectedNode)) {
						this.foundValue = (SimpleNode)root.jjtGetChild(i);
					}		
					searchValue((SimpleNode) root.jjtGetChild(i), value, expectedNode);
					stack.pop();
					stack.printStack();
				}
				/**
				 * @todo: free the last remaining items of stack.
				 */
			}
			// If not its just One-Level
			else {
				return;
			}
		}
		// Usual Case, Checking children from root.
		else {
			// Only happens whenever Node-root has no children and was lastly visited.
			if (root.jjtGetChild(0) == null && stack.getLast() == root.toString()) {
				stack.pop();
				stack.printStack();
				return;
			}

			// Iteration over the Childelements of root.
			for (int i = 0; i < root.jjtGetNumChildren(); i++) {
				stack.push(root.jjtGetChild(i).toString());
				stack.printStack();
				
				if(isNodeValue((SimpleNode)root.jjtGetChild(i), value, expectedNode)) {
					this.foundValue = (SimpleNode)root.jjtGetChild(i);
				}
				
				// Might have children.
				if (this.hasChildren(root.jjtGetChild(i))) {
					searchValue((SimpleNode) root.jjtGetChild(i), value, expectedNode);
					stack.pop();
					stack.printStack();

				}
				// Maybe not than, visited and pop
				else {
					stack.pop();
					stack.printStack();
				}
			}
		}
	}
	
	/**
	 * Method Searches parse tree for node, saves node globally in found.
	 * @param root Node of parse-tree.
	 * @param Node as String to search for in parse tree.
	 */
	protected void searchNode(SimpleNode root, String expectedNode) {
		// Special case its root of tree
		if (root.jjtGetParent() == null) {
			// Push root to stack
			stack.push(root.toString());
			stack.printStack();
			if(isNode((SimpleNode)root, expectedNode)) {
				this.foundNodes.add(root);
			}
			
			// Maybe root has children
			if (this.hasChildren(root)) {
				for (int i = 0; i < root.jjtGetNumChildren(); i++) {
					stack.push(root.jjtGetChild(i).toString());
					stack.printStack();
					
					if(isNode((SimpleNode)root.jjtGetChild(i), expectedNode)) {
						this.foundNodes.add((SimpleNode)root.jjtGetChild(i));
					}		
					searchNode((SimpleNode) root.jjtGetChild(i), expectedNode);
					stack.pop();
					stack.printStack();
				}
				/**
				 * @todo: free the last remaining items of stack.
				 */
			}
			// If not its just One-Level
			else {
				return;
			}
		}
		// Usual Case, Checking children from root.
		else {
			// Only happens whenever Node-root has no children and was lastly visited.
			if (root.jjtGetChild(0) == null && stack.getLast() == root.toString()) {
				stack.pop();
				stack.printStack();
				return;
			}

			// Iteration over the Childelements of root.
			for (int i = 0; i < root.jjtGetNumChildren(); i++) {
				stack.push(root.jjtGetChild(i).toString());
				stack.printStack();
				
				if(isNode((SimpleNode)root.jjtGetChild(i), expectedNode)) {
					this.foundNodes.add((SimpleNode)root.jjtGetChild(i));
				}
				
				// Might have children.
				if (this.hasChildren(root.jjtGetChild(i))) {
					searchNode((SimpleNode) root.jjtGetChild(i), expectedNode);
					stack.pop();
					stack.printStack();

				}
				// Maybe not than, visited and pop
				else {
					stack.pop();
					stack.printStack();
				}
			}
		}
	}

	/**
	 * @param Root element of Node.
	 * @return True if number of Children is larger than 0.
	 */
	public boolean hasChildren(Node root) {
		if (root.jjtGetNumChildren() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param Root element of Node. Used to print all the Child elements of Node.
	 */
	public void printChildren(Node root) {
		if (this.hasChildren(root)) {
			for (int i = 0; i < root.jjtGetNumChildren(); i++) {
				System.out.println(root.jjtGetChild(i));
			}
		} else {
			return;
		}
	}
	
	/**
	 * @param root to check Sibilings on.
	 * @param value you are searching for.
	 * @return true if value found false if not.
	 * @throws NullPointerException when Parent is null.
	 */
	public boolean isValueInSibiling(SimpleNode root, String value) throws NullPointerException{
		if(root.jjtGetParent() == null) {
			throw new NullPointerException("Parent is null. Parse tree depth 0.");
		}
		root = (SimpleNode)root.jjtGetParent();
		for(int i = 0; i<root.jjtGetNumChildren();i++) {
			SimpleNode tmp = (SimpleNode)root.jjtGetChild(i);
			if(tmp.jjtGetValue().toString() == value) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param Node that should be checked
	 * @param Value that should be checked against
	 * @return true if it has the value and false if not
	 */
	public boolean isNodeValue(SimpleNode root, String value, String expectedNode) {
		if(root.jjtGetValue() != null && root.jjtGetValue().toString().equals(value) && root.toString().equals(expectedNode)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param Node that should be checked
	 * @return true if it has the value and false if not
	 */
	public boolean isNode(SimpleNode root, String expectedNode) {
		if(root != null && root.toString().equals(expectedNode)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Frees all Values from LinkList of Class.
	 */
	public void free() {
		this.foundNodes.clear();
		this.foundValue = null;
	}
}
