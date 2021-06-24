package common.logging.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common.logging.aop.IConstant.AppConstants;
import common.logging.aop.LoggerHelper;
import common.logging.utils.LogUtils;

/**
 * The Class LogImp.
 */
@Component
public class LogImp implements Log {

	/** The log utils. */
	@Autowired
	private LogUtils logUtils;
	/** The logger. */
	private final Logger logger = LogManager.getLogger(LogImp.class);
	
	private static final String FLD_CI = "correlationId";

	/**
	 * Instantiates a new log imp.
	 */
	public LogImp() {
	}

	public final void initialize(final String level) {
		LogManager.getRootLogger().setLevel(Level.toLevel(level));
	}

	@Override
	public Level getLogLevel() {
		return logger.getLevel();
	}

	public final void setLogLevel(String logLevel) {
		if (logLevel != null) {
			if (logLevel.equals("false")) {
				logLevel = Level.OFF.toString();
			}
			logger.setLevel(Level.toLevel(logLevel));
		}
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public final void writeGlobalError(final HttpServletRequest request, final Exception exception) {
		try {
			getRequestLog(request, exception);
			String stack = Arrays.toString(exception.getStackTrace());
			write(AppConstants.LEVELERROR, LoggerHelper.getCorrelationId(request), logUtils.getUserInfo(request),
					"9001", exception.getMessage(), ExceptionUtils.getRootCauseMessage(exception), stack, null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the request log.
	 *
	 * @param request
	 *            the request
	 * @param exception
	 *            the exception
	 */
	@SuppressWarnings("unchecked")
	private void getRequestLog(HttpServletRequest request, Exception exception) {
		JSONObject jsonObject = new JSONObject();
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();
		String completeURL;
		if (queryString == null) {
			completeURL = requestURL.toString();
		} else {
			completeURL = requestURL.append('?').append(queryString).toString();
		}
		jsonObject.put("requestURI", completeURL);
		jsonObject.put("requestBody", null);
		jsonObject.put("method", request.getMethod());
		write(AppConstants.LEVELERROR, LoggerHelper.getCorrelationId(request), logUtils.getUserInfo(request), "9001",
				jsonObject.toJSONString(), ExceptionUtils.getRootCauseMessage(exception), null, null);
	}

	public final void write(final String severity, final String correlationId, final JSONObject userInfo,
			final String messageCode, final String messageDetail, final String message, final String stackTrace,
			final JSONObject traceInfo) {
		writeLog(severity, correlationId, null, userInfo, messageCode, message, messageDetail, stackTrace, traceInfo);
	}

	public final void writeInfo(final String severity, final String correlationId, final JSONObject userInfo,
			final String messageCode, final String messageDetail, final String message, final String stackTrace,
			final JSONObject traceInfo) {
		writeLog(severity, correlationId, null, userInfo, messageCode, message, messageDetail, stackTrace, traceInfo);
	}

	public final void write(final Exception exception) {
		writeStackTrace(AppConstants.LEVELERROR,getCorrelationId(), null, "9001", ExceptionUtils.getRootCauseMessage(exception),
				exception.getMessage(), exception, null);
	}
	
	public String getCorrelationId() {
		HttpServletRequest request = logUtils.getCurrentRequest();
		String correlationId = "";
		if (request != null) {
			if (request.getAttribute(FLD_CI) == null) {
				if (request.getHeader(FLD_CI) == null) {
					correlationId = UUID.randomUUID().toString();
				} else {
					correlationId = request.getHeader(FLD_CI);
				}
			} else {
				correlationId = (String) request.getAttribute(FLD_CI);
			}

			request.setAttribute(FLD_CI, correlationId);
		}
		return correlationId;
	}

	public final void write(final String severity, final String messageCode, final String messageDetail,
			final String message) {
		writeLog(severity, null, null, null, messageCode, message, messageDetail, null, null);
	}

	public final void writeStackTrace(final String severity, final String correlationId, final JSONObject userInfo,
			final String messageCode, final String messageDetail, final String message, final Exception stackTrace,
			final JSONObject traceInfo) {
		final StringWriter errors = new StringWriter();
		stackTrace.printStackTrace(new PrintWriter(errors));
		writeLog(severity, correlationId, null, userInfo, messageCode, message, messageDetail, errors.toString(),
				traceInfo);
	}

	public void writeInfo(final String severity, final String correlationId, final String reqResMappingId,
			final JSONObject userInfo, final String messageCode, final String messageDetail, final String message,
			final String stackTrace, final JSONObject traceInfo) {
		writeLog(severity, correlationId, reqResMappingId, userInfo, messageCode, message, messageDetail, stackTrace,
				traceInfo);
	}

	/**
	 * Write log.
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
	 * @param message
	 *            the message
	 * @param messageDetail
	 *            the message detail
	 * @param stackTrace
	 *            the stack trace
	 * @param traceInfo
	 *            the trace info
	 */
	private void writeLog(final String severity, final String correlationId, final String reqResMappingId,
			final JSONObject userInfo, final String messageCode, final String message, final String messageDetail,
			final String stackTrace, final JSONObject traceInfo) {
		final String logSeverity = StringUtils.defaultIfBlank(severity, AppConstants.LEVELINFO);
		final String log = getLogString(logSeverity, correlationId, reqResMappingId, userInfo, messageCode, message,
				messageDetail, stackTrace, traceInfo);
		if (AppConstants.LEVELINFO.equals(logSeverity)) {
			logger.info(log);
		} else if (AppConstants.LEVELTRACE.equals(logSeverity)) {
			logger.trace(log);
		} else if (AppConstants.LEVELDEBUG.equals(logSeverity)) {
			logger.debug(log);
		} else if (AppConstants.LEVELERROR.equals(logSeverity)) {
			logger.error(log);
		} else if (AppConstants.LEVELWARN.equals(logSeverity)) {
			logger.warn(log);
		} else {// by default logging will be in info mode.
			logger.info(log);
		}
	}

	/**
	 * Gets the log string.
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
	 * @param message
	 *            the message
	 * @param messageDetail
	 *            the message detail
	 * @param stackTrace
	 *            the stack trace
	 * @param traceInfo
	 *            the trace info
	 * @return the log string
	 */
	@SuppressWarnings("unchecked")
	private String getLogString(final String severity, final String correlationId, final String reqResMappingId,
			final JSONObject userInfo, final String messageCode, final String message, final String messageDetail,
			final String stackTrace, final JSONObject traceInfo) {
		final JSONObject jsonObject = new JSONObject();
		jsonObject.put(AppConstants.SEVERITY, severity);
		jsonObject.put(AppConstants.CORRELATIONID, correlationId);
		jsonObject.put(AppConstants.REQRESMAPPINGID, reqResMappingId);
		jsonObject.put(AppConstants.USERINFO, userInfo);
		jsonObject.put(AppConstants.MESSAGECODE, messageCode);
		jsonObject.put(AppConstants.MESSAGE, message);
		jsonObject.put(AppConstants.MESSAGEDETAIL, messageDetail);
		jsonObject.put(AppConstants.TRACEINFO, traceInfo);
		jsonObject.put(AppConstants.STACKTRACE, stackTrace);
		return jsonObject.toJSONString();
	}

}
