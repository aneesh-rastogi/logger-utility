package common.logging.aop;

import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * The Class ApplicationLevelException.
 */
public class CustomException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The error. */
	private List<Error> errors;
	
	/** The http status. */
	private HttpStatus httpStatus;
	
	/**
	 * Instantiates a new application level exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public CustomException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Instantiates a new application level exception.
	 *
	 * @param ex the ex
	 */
	public CustomException(Exception ex) {
		super(ex);
	}
	
	/**
	 * Instantiates a new application level exception.
	 *
	 * @param errors the errors
	 * @param httpStatus the http status
	 */
	public CustomException(final List<Error> errors, HttpStatus httpStatus) {
		this.errors = errors;
		this.setHttpStatus(httpStatus);
	}
	
	public CustomException(final List<Error> errors) {
		this.errors = errors;
	}

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public List<Error> getErrors() {
		return errors;
	}

	/**
	 * Sets the errors.
	 *
	 * @param errors the new errors
	 */
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the http status.
	 *
	 * @param httpStatus the new http status
	 */
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
