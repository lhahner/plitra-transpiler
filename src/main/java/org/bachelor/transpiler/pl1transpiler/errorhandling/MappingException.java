package org.bachelor.transpiler.pl1transpiler.errorhandling;

/**
 * The MappingException should be thrown 
 * whenever the Mapping is not possible
 * or the input PL/I Data is somehow wrong 
 * or not usable.
 * 
 * @author lennart.hahner
 */
public class MappingException extends Exception{
	
	/** serialiableID */
	private static final long serialVersionUID = -3318241684147138827L;

	/**
	 * Constructs an LexicalErrorException with null
	 * as its error detail message
	 */
	public MappingException() {
		super("Error occured while Mapping the Parsetree.");
	}
	
	/**
	 * Constructs an MappingException with the specified detail message.
	 * 
	 * @param errorMessage
	 */
	public MappingException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Constructs an MappingException with the specified detail message
     * and cause.
	 * 
	 * @param errorMessage
	 * @param cause
	 */
	public MappingException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
	/**
	 * Constructs an MappingException with the specified cause.
	 * 
	 * @param cause
	 */
	public MappingException(Throwable cause) {
		super(cause);
	}
}
