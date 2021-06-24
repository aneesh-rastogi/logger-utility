package common.logging.aop;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Error.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error{
	
	/** The id. */
	@JsonProperty("id")
	private String id;
	
	/** The status. */
	@JsonProperty("status")
	private int status;
	
	/** The code. */
	@JsonProperty("code")
	private String code;
	
	/** The title. */
	@JsonProperty("title")
	private String title;
	
	/** The detail. */
	@JsonProperty("detail")
	private String detail;
	
	/** The inner error. */
	@JsonProperty("innerError")
	private Error innerError;
	
	@JsonIgnore
	private HttpStatus httpStatus;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@JsonProperty("id")
	public String getId() {
	return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
	this.id = id;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	@JsonProperty("status")
	public int getStatus() {
	return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	@JsonProperty("status")
	public void setStatus(int status) {
	this.status = status;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	@JsonProperty("code")
	public String getCode() {
	return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	@JsonProperty("code")
	public void setCode(String code) {
	this.code = code;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	@JsonProperty("title")
	public String getTitle() {
	return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	@JsonProperty("title")
	public void setTitle(String title) {
	this.title = title;
	}

	/**
	 * Gets the detail.
	 *
	 * @return the detail
	 */
	@JsonProperty("detail")
	public String getDetail() {
	return detail;
	}

	/**
	 * Sets the detail.
	 *
	 * @param detail the new detail
	 */
	@JsonProperty("detail")
	public void setDetail(String detail) {
	this.detail = detail;
	}

	/**
	 * Gets the inner error.
	 *
	 * @return the inner errors
	 */
	@JsonProperty("innerError")
	public Error getInnerError() {
	return innerError;
	}

	/**
	 * Sets the inner error.
	 *
	 * @param innerError the new inner error
	 */
	@JsonProperty("innerError")
	public void setInnerError(Error innerError) {
	this.innerError = innerError;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Error [id=" + id + ", status=" + status + ", code=" + code + ", title=" + title + ", detail=" + detail
				+ ", innerError=" + innerError + ", httpStatus=" + httpStatus + "]";
	}

	/**
	 * Instantiates a new error.
	 */
	public Error() {}
	
	/**
	 * Instantiates a new error.
	 *
	 * @param id the id
	 * @param status the status
	 * @param code the code
	 * @param title the title
	 * @param detail the detail
	 * @param innerError the inner error
	 * @param httpStatus the http status
	 */
	public Error(String id, int status, String code, String title, String detail, Error innerError, HttpStatus httpStatus) {
		super();
		this.id = id;
		this.status = status;
		this.code = code;
		this.title = title;
		this.detail = detail;
		this.innerError = innerError;
		this.httpStatus  = httpStatus;
	}
}
