package common.logging.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.json.simple.JSONObject;

/**
 * The Class Log manager.
 */
public interface Log {

	/**
	 * Initialize.
	 *
	 * @param level
	 *            the level
	 */
	void initialize(String level);

	/**
	 * write exception.
	 *
	 * @param request
	 *            the request
	 * @param ex
	 *            the ex
	 */
	void writeGlobalError(HttpServletRequest request, Exception ex);

	/**
	 * Write.
	 *
	 * @param exception
	 *            the exception
	 */
	void write(Exception exception);

	/**
	 * Gets the log level.
	 *
	 */
	Level getLogLevel();

	/**
	 * Sets the log level.
	 *
	 * @param logLevel
	 *            the log level
	 *            the hibernate log level
	 */
	void setLogLevel(String logLevel);

	/**
	 * Write.
	 *
	 * @param severity
	 *            the severity
	 * @param correlationId
	 *            the correlation id
	 * @param userInfo
	 *            the user info
	 * @param messageCode
	 *            the message code
	 * @param messageDetail
	 *            the message detail
	 * @param message
	 *            the message
	 * @param stackTrace
	 *            the stack trace
	 * @param traceInfo
	 *            the trace info
	 */
	void write(String severity, String correlationId, JSONObject userInfo, String messageCode, String messageDetail,
			String message, String stackTrace, JSONObject traceInfo);

	/**
	 * Write info.
	 *
	 * @param severity
	 *            the severity
	 * @param correlationId
	 *            the correlation id
	 * @param userInfo
	 *            the user info
	 * @param messageCode
	 *            the message code
	 * @param messageDetail
	 *            the message detail
	 * @param message
	 *            the message
	 * @param stackTrace
	 *            the stack trace
	 * @param traceInfo
	 *            the trace info
	 */
	void writeInfo(String severity, String correlationId, JSONObject userInfo, String messageCode, String messageDetail,
			String message, String stackTrace, JSONObject traceInfo);

	/**
	 * Write.
	 *
	 * @param severity
	 *            the severity
	 * @param messageCode
	 *            the message code
	 * @param messageDetail
	 *            the message detail
	 * @param message
	 *            the message
	 */
	void write(String severity, String messageCode, String messageDetail, String message);

	/**
	 * Write stack trace.
	 *
	 * @param severity
	 *            the severity
	 * @param correlationId
	 *            the correlation id
	 * @param userInfo
	 *            the user info
	 * @param messageCode
	 *            the message code
	 * @param messageDetail
	 *            the message detail
	 * @param message
	 *            the message
	 * @param stackTrace
	 *            the stack trace
	 * @param traceInfo
	 *            the trace info
	 */
	void writeStackTrace(String severity, String correlationId, JSONObject userInfo, String messageCode,
			String messageDetail, String message, Exception stackTrace, JSONObject traceInfo);

	/**
	 * Write info.
	 *
	 * @param severity
	 *            the severity
	 * @param correlationId
	 *            the correlation id
	 * @param reqResMappingId
	 *            the req res mapping id
	 * @param userInfo
	 *            the user info
	 * @param messageCode
	 *            the message code
	 * @param messageDetail
	 *            the message detail
	 * @param message
	 *            the message
	 * @param stackTrace
	 *            the stack trace
	 * @param traceInfo
	 *            the trace info
	 */
	public void writeInfo(final String severity, final String correlationId, final String reqResMappingId,
			final JSONObject userInfo, final String messageCode, final String messageDetail, final String message,
			final String stackTrace, final JSONObject traceInfo);

	/**
	 * Checks if is trace enabled.
	 *
	 * @return true, if is trace enabled
	 */
	boolean isTraceEnabled();

	/**
	 * Checks if is debug enabled.
	 *
	 * @return true, if is debug enabled
	 */
	boolean isDebugEnabled();

	/**
	 * Checks if is info enabled.
	 *
	 * @return true, if is info enabled
	 */
	boolean isInfoEnabled();

}
