package common.logging.aop;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class ErrorResponse.
 */
public class ResponseException{

	/** The error. */
	@JsonProperty("errors")
	private List<Error> errors;

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	@JsonProperty("errors")
	public List<Error> getErrors() {
		return errors;
	}

	/**
	 * Sets the error.
	 */
	@JsonProperty("errors")
	public void setError(List<Error> errors) {
		this.errors = errors;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResponseException [errors=" + errors + "]";
	}

	/**
	 * Instantiates a new error response.
	 */
	public ResponseException() {
	}

	public ResponseException(List<Error> errors) {
		super();
		this.errors = errors;
	}

}
