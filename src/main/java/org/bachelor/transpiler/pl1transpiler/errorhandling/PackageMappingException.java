package org.bachelor.transpiler.pl1transpiler.errorhandling;

public class PackageMappingException extends Exception{
	/**
	 * Constructs an PackageMappingExceptionextends with null
	 * as its error detail message
	 */
	public PackageMappingException() {
	}
	
	/**
	 * Constructs an PackageMappingExceptionextends with the specified detail message.
	 * 
	 * @param errorMessage
	 */
	public PackageMappingException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Constructs an PackageMappingExceptionextends with the specified detail message
     * and cause.
	 * 
	 * @param errorMessage
	 * @param cause
	 */
	public PackageMappingException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
	/**
	 * Constructs an PackageMappingExceptionextends with the specified cause.
	 * 
	 * @param cause
	 */
	public PackageMappingException(Throwable cause) {
		super(cause);
	}
}
