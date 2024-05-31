package org.bachelor.transpiler.pl1transpiler.checker;

/**
 * @author lennart.hahner
 * @param <T> Stack uses generics to define Stack type.
 */
public class Stack <T>{
	int top = -1;
	int size = 0;
	Object content[];
	
	/**
	 * @param size of Stack.
	 */
	public Stack(int size){
		content = new Object[size];
		this.size = size;
		top++;
	}
	
	/**
	 * @param c content that should be pushed on the Stack.
	 * @throws StackOverflowError
	 */
	public void push(T c) throws StackOverflowError{
		if(this.size < this.content.length) {
			throw new StackOverflowError("Size exeded");
		}
		content[top] = c;
		top++;
		
	}
	
	/**
	 * Removes last pushed content from Stack.
	 * @throws StackOverflowError
	 */
	public void pop() throws StackOverflowError{
		if(size == 0) {
			throw new StackOverflowError("Size exeeded");
		}
		content[top] = 0;
		top--;
	}
	
	/**
	 * prints Stack content;
	 */
	public void printStack() {
		for(int i=0;i<content.length;i++) {
			if(content[i] == null)
				continue;
			System.out.println(content[i]);
		}
		System.out.println("----------------------");
	}
	
	/**
	 * @return true if stack has length 0 else return false.
	 */
	public boolean isEmpty() {
		if(content.length == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @return get last element from Stack.
	 */
	public Object getLast() {
		return content[top];
	}
}
