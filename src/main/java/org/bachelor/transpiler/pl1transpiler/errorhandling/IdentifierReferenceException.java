package org.bachelor.transpiler.pl1transpiler.errorhandling;

public class IdentifierReferenceException extends Exception {
	/**
	 * Constructs an IdentifierReferenceException with null
	 * as its error detail message
	 */
	public IdentifierReferenceException() {
	}
	
	/**
	 * Constructs an IdentifierReferenceException with the specified detail message.
	 * 
	 * @param errorMessage
	 */
	public IdentifierReferenceException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Constructs an IdentifierReferenceException with the specified detail message
     * and cause.
	 * 
	 * @param errorMessage
	 * @param cause
	 */
	public IdentifierReferenceException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
	/**
	 * Constructs an IdentifierReferenceException with the specified cause.
	 * 
	 * @param cause
	 */
	public IdentifierReferenceException(Throwable cause) {
		super(cause);
	}
}
