package org.bachelor.transpiler.pl1transpiler.errorhandling;

public class IncorrectInputFileException extends Exception{
	/**
	 * Constructs an IncorrectInputException with null
	 * as its error detail message
	 */
	public IncorrectInputFileException() {
	}
	
	/**
	 * Constructs an IncurrectInputFileException with the specified detail message.
	 * 
	 * @param errorMessage
	 */
	public IncorrectInputFileException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Constructs an IncorrectInputFileException with the specified detail message
     * and cause.
	 * 
	 * @param errorMessage
	 * @param cause
	 */
	public IncorrectInputFileException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
	/**
	 * Constructs an IncorrectFileException with the specified cause.
	 * 
	 * @param cause
	 */
	public IncorrectInputFileException(Throwable cause) {
		super(cause);
	}
}
