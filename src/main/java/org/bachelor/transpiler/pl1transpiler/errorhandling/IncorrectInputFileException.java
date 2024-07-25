package org.bachelor.transpiler.pl1transpiler.errorhandling;

/**
 * This Exception should be used whenever the
 * user tries to input a file other then type
 * of <code>.pli</code>.
 * 
 * @author lennart.hahner
 */
public class IncorrectInputFileException extends Exception{
	
	/** serialzableID */
	private static final long serialVersionUID = -8562244738573059586L;

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
