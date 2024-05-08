package org.bachelor.transpiler.pl1transpiler.errorhandling;

public class LexicalErrorException extends Exception{
	/**
	 * Constructs an LexicalErrorException with null
	 * as its error detail message
	 */
	public LexicalErrorException() {
	}
	
	/**
	 * Constructs an LexicalErrorException with the specified detail message.
	 * 
	 * @param errorMessage
	 */
	public LexicalErrorException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Constructs an LexicalErrorException with the specified detail message
     * and cause.
	 * 
	 * @param errorMessage
	 * @param cause
	 */
	public LexicalErrorException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
	/**
	 * Constructs an LexicalErrorException with the specified cause.
	 * 
	 * @param cause
	 */
	public LexicalErrorException(Throwable cause) {
		super(cause);
	}
}
