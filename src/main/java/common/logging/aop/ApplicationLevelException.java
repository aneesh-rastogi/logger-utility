package common.logging.aop;

/**
 * The Class ApplicationLevelException.
 */
public class ApplicationLevelException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new application level exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ApplicationLevelException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Instantiates a new application level exception.
	 *
	 * @param ex the ex
	 */
	public ApplicationLevelException(Exception ex) {
		super(ex);
	}
	
}
