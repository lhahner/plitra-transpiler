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
	SymbolTable st = new SymbolTable();
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
	public void checkHierachie(SimpleNode root) {
		 
		//Special case its root of tree
		if(root.jjtGetParent() == null) {
			//Push root to stack
			stack.push(root.toString());
			stack.printStack();
			if(isHierachieError(root)) {
				System.out.println("hierachical error");
			}
			
			//Maybe root has children
			if(this.hasChildren(root)) {
				
				for(int i=0; i<root.jjtGetNumChildren();i++) {
					stack.push(root.jjtGetChild(i).toString());
					stack.printStack();
					if(isHierachieError((SimpleNode)root.jjtGetChild(i))) {
						System.out.println("hierachical error");
					}
						
					checkHierachie((SimpleNode)root.jjtGetChild(i));
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
				if(isHierachieError((SimpleNode)root.jjtGetChild(i))) {
					System.out.println("hierachical error");
				}
				
				//Might have children.
				if(this.hasChildren(root.jjtGetChild(i))) {
					
					checkHierachie((SimpleNode)root.jjtGetChild(i));
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
	 * @param Node which is Sibling of Id Node.
	 * @return Id Node to get Values from.
	 */
	public SimpleNode getIdSibiling(SimpleNode root) {
		root = (SimpleNode)root.jjtGetParent();
		for(int i = 0; i<root.jjtGetNumChildren();i++) {
			if(root.jjtGetChild(i).toString() == "id") {
				return (SimpleNode)root.jjtGetChild(i);
			}
		}
		return null;
	}
	
	/**
	 * @param Id Node of Parse-Tree
	 * @return True if hierachy of parent identifier is larger than child identifier
	 */
	public boolean isHierachieError(SimpleNode root) {
		if(root.toString() == "id" && root.jjtGetParent() != null) {
			SimpleNode idParent = this.getIdSibiling((SimpleNode)root.jjtGetParent());
			String[] idValuesParent = (String [])idParent.jjtGetValue();
			String[] idValuesRoot = (String [])root.jjtGetValue();
			
			int hierachieParent = Integer.parseInt(idValuesParent[1]);
			int hierachieRoot = Integer.parseInt(idValuesRoot[1]);
			
			if(hierachieParent > hierachieRoot) {
				return true;
			}
			
		}
		return false;
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
