package org.bachelor.transpiler.pl1transpiler.checker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bachelor.transpiler.pl1transpiler.parser.Node;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1Parser;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.scanner.InputReader;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

public class HierachieChecker extends Checker{
	
	/**
	 * @param Root Element of Tree.
	 * This Method will Loop through the Entire Parse-Tree.
	 * It Utilizes the In-Depth-First Search Algorhitem.
	 * Each visited Node will be pushed to Stack.
	 */
	public void searchHiericalError(SimpleNode root) {
		 
		//Special case its root of tree
		if(root.jjtGetParent() == null) {
			//Push root to stack
			tracker.push(root.toString());
			tracker.printStack();
			if(isHierachieError(root)) {
				System.out.println("hierachical error");
			}
			
			//Maybe root has children
			if(this.hasChildren(root)) {
				
				for(int i=0; i<root.jjtGetNumChildren();i++) {
					tracker.push(root.jjtGetChild(i).toString());
					tracker.printStack();
					if(isHierachieError((SimpleNode)root.jjtGetChild(i))) {
						System.out.println("hierachical error");
					}
						
					searchHiericalError((SimpleNode)root.jjtGetChild(i));
					tracker.printStack();
						
					tracker.pop();
					tracker.printStack();
					
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
			if(root.jjtGetChild(0) == null && tracker.getLast() == root.toString()) {
				tracker.pop();
				tracker.printStack();
				
				return;
			}
			
			//Iteration over the Childelements of root.
			for(int i=0;i<root.jjtGetNumChildren();i++) {
				tracker.push(root.jjtGetChild(i).toString());
				tracker.printStack();
				if(isHierachieError((SimpleNode)root.jjtGetChild(i))) {
					System.out.println("hierachical error");
				}
				
				//Might have children.
				if(this.hasChildren(root.jjtGetChild(i))) {
					
					searchHiericalError((SimpleNode)root.jjtGetChild(i));
					tracker.printStack();
					
					tracker.pop();
					tracker.printStack();

				}
				
				//Maybe not than, visited and pop
				else {
					tracker.pop();
					tracker.printStack();
					
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
			if(root.jjtGetChild(i).toString() == "Id") {
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
		if(root.toString() == "Id" && root.jjtGetParent() != null) {
			SimpleNode idParent = this.getIdSibiling((SimpleNode)root.jjtGetParent());
			if(idParent == null) {
				return false;
			}
			String[] idValuesParent = (String [])idParent.jjtGetValue();
			String[] idValuesChild = (String [])root.jjtGetValue();
			
			int hierachieParent = Integer.parseInt(idValuesParent[1]);
			int hierachieChild = Integer.parseInt(idValuesChild[1]);
			
			if(hierachieParent > hierachieChild) {
				return true;
			}
			else if(hierachieParent == hierachieChild) {
				return true;
			}
			
		}
		return false;
	}
	
}
