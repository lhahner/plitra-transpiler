package org.bachelor.transpiler.pl1transpiler.errorhandling;

/**
 * A LexcialErrorException can be used for any lexical
 * Error. A valid lexical error is either identified
 * by the lexical analysation or by any other similar 
 * process. For Example the Mapping of the Picture
 * Pattern Expression is utilizing this Exception.
 * 
 * 
 * @author lennart.hahner
 */

public class LexicalErrorException extends Exception{


	/** serialzableID */
	private static final long serialVersionUID = -2004514597360506773L;

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
