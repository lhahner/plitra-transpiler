package org.bachelor.transpiler.pl1transpiler.checker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bachelor.transpiler.pl1transpiler.lexer.InputReader;
import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

public class TraverseParseTree{
	
	/**
	 * Stack of type String. 
	 */
	Stack<String> stack = new Stack<String>(256);
	Map<Integer, String> resultMap = new HashMap();
	
	/**
	 * @param root
	 */
	public TraverseParseTree() {
	}
	
	/**
	 * @param Root Element of Tree.
	 * This Method will Loop through the Entire Parse-Tree.
	 * It Utilizes the In-Depth-First Search Algorhitem.
	 * Each visited Node will be pushed to Stack.
	 */
	public void readDepthFirst(SimpleNode root) {
		
		//Special case its root of tree
		if(root.jjtGetParent() == null) {
			//Push root to stack
			stack.push(root.toString());
			stack.printStack();
			
			//Maybe root has children
			if(this.hasChildren(root)) {
				
				for(int i=0; i<root.jjtGetNumChildren();i++) {
					stack.push(root.jjtGetChild(i).toString());
					stack.printStack();
					readDepthFirst((SimpleNode)root.jjtGetChild(i));
					stack.printStack();
					stack.pop();
					stack.printStack();
				}
				/**
				 * @todo: free the last remaining items of stack. 
				 */
			}
			//If not its just One-Level
			else {
				return;
			}
		}
		//Usual Case, Checking children from root.
		else {
			//Only happens whenever Node-root has no children and was lastly visited.
			if(root.jjtGetChild(0) == null && stack.getLast() == root.toString()) {
				stack.pop();
				stack.printStack();
				return;
			}
			
			//Iteration over the Childelements of root.
			for(int i=0;i<root.jjtGetNumChildren();i++) {
				stack.push(root.jjtGetChild(i).toString());
				stack.printStack();
				
				//Might have children.
				if(this.hasChildren(root.jjtGetChild(i))) {
					
					readDepthFirst((SimpleNode)root.jjtGetChild(i));
					stack.printStack();
					stack.pop();
					stack.printStack();
				}
				
				//Maybe not than, visited and pop
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
		if(root.jjtGetNumChildren() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param Root element of Node.
	 * Used to print all the Child elements of Node.
	 */
	public void printChildren(Node root) {
		if(this.hasChildren(root)) {
			for(int i = 0;i<root.jjtGetNumChildren();i++) {
				System.out.println(root.jjtGetChild(i));
			}
		}
		else {
			return;
		}
	}
}
