package org.bachelor.transpiler.pl1transpiler.errorhandling;

public class TypeMappingException extends Exception{
	/**
	 * Constructs an TypeMappingException with null
	 * as its error detail message
	 */
	public TypeMappingException() {
	}
	
	/**
	 * Constructs an TypeMappingException with the specified detail message.
	 * 
	 * @param errorMessage
	 */
	public TypeMappingException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Constructs an TypeMappingException with the specified detail message
     * and cause.
	 * 
	 * @param errorMessage
	 * @param cause
	 */
	public TypeMappingException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
	/**
	 * Constructs an TypeMappingException with the specified cause.
	 * 
	 * @param cause
	 */
	public TypeMappingException(Throwable cause) {
		super(cause);
	}
}