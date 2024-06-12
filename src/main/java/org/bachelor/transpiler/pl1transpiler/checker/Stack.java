package org.bachelor.transpiler.pl1transpiler.checker;

/**
 * @author lennart.hahner
 * @param <T> Stack uses generics to define Stack type.
 */
public class Stack <T>{
	int top = -1;
	int size = 0;
	T[] content;
	
	/**
	 * @param size of Stack.
	 */
	public Stack(int size){
		content = (T[]) new Object[size];
		this.size = size;
	}
	
	
	/**
	 * @param c content that should be pushed on the Stack.
	 * @throws StackOverflowError
	 */
	public T push(T c) throws ArrayIndexOutOfBoundsException{
		if(this.size < this.content.length) {
			throw new ArrayIndexOutOfBoundsException("Size exeded");
		}
		top++;
		content[top] = c;
		T current = content[top];
		return current;
	}
	
	/**
	 * Removes last pushed content from Stack.
	 * @throws StackOverflowError
	 */
	public T pop() throws StackOverflowError{
		if(top == -1) {
			throw new StackOverflowError("Size exeeded");
		}
		content[top] = null;
		top--;
		if(top == -1)
			return null;
		return content[top];
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
		for(int i = 0;i<content.length;i++) {
			if(content[i] != null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return get last element from Stack.
	 */
	public Object getLast() {
		if(top == -1)
			return null;
		return content[top];
	}
	
	public void free() {
		if(content == null) {
			return;
		}
		while(top >= 0) {
			this.pop();
		}
	}
}
