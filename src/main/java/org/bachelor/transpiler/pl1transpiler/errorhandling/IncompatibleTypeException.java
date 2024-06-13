package org.bachelor.transpiler.pl1transpiler.errorhandling;

public class IncompatibleTypeException extends Exception {
	/**
	 * Constructs an IncompatibleTypeException with null
	 * as its error detail message
	 */
	public IncompatibleTypeException() {
	}
	
	/**
	 * Constructs an IncompatibleTypeException with the specified detail message.
	 * 
	 * @param errorMessage
	 */
	public IncompatibleTypeException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Constructs an IdentifierReferenceException with the specified detail message
     * and cause.
	 * 
	 * @param errorMessage
	 * @param cause
	 */
	public IncompatibleTypeException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
	/**
	 * Constructs an IdentifierReferenceException with the specified cause.
	 * 
	 * @param cause
	 */
	public IncompatibleTypeException(Throwable cause) {
		super(cause);
	}
}
