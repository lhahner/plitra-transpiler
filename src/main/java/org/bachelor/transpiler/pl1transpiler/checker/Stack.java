package org.bachelor.transpiler.pl1transpiler.checker;

import java.util.ArrayList;

/**
 * The Class Stack should only be used in the
 * Checker Module and also only for tracking purposes while searching the
 * parse Tree. This should not be utilized for any other needs. 
 *
 * @author lennart.hahner
 * @param <T> Defines Type of Stack.
 */
public class Stack <T>{
	
	/** The top. */
	int top = -1;
	
	/** The content. */
	ArrayList<T> list;
	
	/**
	 * Instantiates a new stack.
	 *
	 * @param size of Stack.
	 */
	public Stack(){
		list = new ArrayList<T>();
	}
	
	
	/**
	 * This method pushes the value provided
	 * as parameter on top of the ArrayList.
	 * Also the pointer, which always points on the
	 * top of the list will increase.
	 *
	 * @param value		The Value defined by the Type of the Stack
	 * 					that should be put on top of the stack.
	 * 
	 * @return The current element that is on top of the stack.
	 */
	public T push(T value) {
		top++;
		list.add(top, value);
		T current = list.get(top);
		return current;
	}
	
	/**
	 * Removes last pushed content from Stack.
	 * Also decreases the pointer that points to the last element.
	 *
	 * @return the element that is currently on top of the stack.
	 * @throws StackOverflowError Only when trying to pop from an empty Stack
	 */
	public T pop() throws StackOverflowError{
		if(top == -1) {
			throw new StackOverflowError("Size exeeded");
		}
		list.remove(top);
		top--;
		if(top == -1)
			return null;
		return list.get(top);
	}
	
	/**
	 * Prints the content thats currently in Stack.
	 */
	public void printStack() {
		for(T i : list) {
			if(i == null)
				continue;
			System.out.println(i);
		}
		System.out.println("----------------------");
	}
	
	/**
	 * Checks if is empty.
	 *
	 * @return true if stack has length 0 else return false.
	 */
	public boolean isEmpty() {
		for(T i : list) {
			if(i != null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Will give the last element pushed on stack.
	 *
	 * @return get last element from Stack.
	 */
	public Object getLast() {
		if(top == -1)
			return null;
		return list.get(top);
	}
	
	/**
	 * this removes all elements from Stack
	 * until the Stack is empty.
	 */
	public void free() {
		if(list == null) {
			return;
		}
		while(top >= 0) {
			this.pop();
		}
	}
}
