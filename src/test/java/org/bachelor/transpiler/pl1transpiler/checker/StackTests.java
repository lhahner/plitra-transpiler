package org.bachelor.transpiler.pl1transpiler.checker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StackTests {
	
	Stack <String> stack;
	private final static int SIZE = 5;
	
	@BeforeEach
	void init(){
		stack = new Stack<String>(SIZE);
	}
	
	@Test
	@DisplayName("Push Should Work")
	public void pushToStack_setStringOnTop() {
		assertEquals("foo", stack.push("foo"));
		assertEquals("baa", stack.push("baa"));
	}
	
	@Test
	@DisplayName("Push Should Work, different Type")
	public void pushToStack_setIntOnTop() {
		Stack<Integer> stack = new Stack<Integer>(SIZE);
		assertEquals(1, stack.push(1));
		assertEquals(2, stack.push(2));
	}
	
	@Test
	@DisplayName("Push Should Not Work")
	public void pushToStack_ArrayIndexOutOfBoundsException() {
		String[] shouldNotWork = {"foo","baa","foo","baa","foo"};
		String shouldBeException = "baa";
		for(int i=0;i<shouldNotWork.length;i++) {
			stack.push(shouldNotWork[i]);
		}
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			stack.push(shouldBeException);
		});
	}
	
	
	@Test
	@DisplayName("Pop Should Work")
	public void popStack_removedTop() {
		Stack<Integer> localStack = new Stack<Integer>(SIZE);
		localStack.push(1);
		localStack.push(2);
		assertEquals(1, localStack.pop());
	}
	
	@Test
	@DisplayName("Pop Should Not Work")
	public void popStack_StackOverflowExpected() {
	Stack<Integer> localStack = new Stack<Integer>(SIZE);
		assertThrows(StackOverflowError.class, () -> {
			localStack.pop();
			});
	}
	
	@Test
	@DisplayName("Free should free memory")
	public void freeStack_stackShouldBeZero() {
		stack.push("foo");
		stack.push("baa");
		stack.push("baz");
		
		stack.free();
		boolean empty = stack.getLast() == null? true : false;
		assertTrue(empty);
	}
}
