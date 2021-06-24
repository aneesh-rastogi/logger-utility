package common.logging.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import common.logging.aop.IConstant;
import common.logging.aop.LogApplicationSettings;
import common.logging.aop.LoggerHelper;
import common.logging.interceptor.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.jsoftbiz.utils.OS;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class LogUtils {

	@Autowired
	private Log logger;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public LogUtils() {
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public void writeStartLogs(final ProceedingJoinPoint proceedingJoinPoint, final long startTime,
			final String logLevel, final HttpServletRequest request) {
		final String payload = getRequestPayLoad(request);
		logger.writeInfo(logLevel, LoggerHelper.getCorrelationId(request), LoggerHelper.getReqResMappingId(request),
				getUserInfo(request), "5001", payload, getRequestUrl(request), null,
				getTraceInfoStart(getTraceInfo(proceedingJoinPoint), startTime));
	}

	public void writeStartLogsWithoutPayload(final ProceedingJoinPoint proceedingJoinPoint, final long startTime,
			final String logLevel, final HttpServletRequest request) {
		logger.writeInfo(logLevel, LoggerHelper.getCorrelationId(request), LoggerHelper.getReqResMappingId(request),
				getUserInfo(request), "5001", null, getRequestUrl(request), null,
				getTraceInfoStart(getTraceInfo(proceedingJoinPoint), startTime));
	}

	public void writeEndLogs(final ProceedingJoinPoint proceedingJoinPoint, final long startTime, final Object value,
			final String logLevel, final HttpServletRequest request) throws IOException {

		final String response = objectMapper.writeValueAsString(value);
		final long endTime = System.currentTimeMillis();
		logger.writeInfo(logLevel, LoggerHelper.getCorrelationId(request), LoggerHelper.getReqResMappingId(request),
				getUserInfo(request), "5002", response, getRequestUrl(request), null,
				getTraceInfoEnd(getTraceInfo(proceedingJoinPoint), startTime, endTime));
	}

	public void writeEndLogsWithoutPayload(final ProceedingJoinPoint proceedingJoinPoint, final long startTime,
			final Object value, final String logLevel, final HttpServletRequest request) throws IOException {

		final long endTime = System.currentTimeMillis();
		logger.writeInfo(logLevel, LoggerHelper.getCorrelationId(request), LoggerHelper.getReqResMappingId(request),
				getUserInfo(request), "5002", null, getRequestUrl(request), null,
				getTraceInfoEnd(getTraceInfo(proceedingJoinPoint), startTime, endTime));
	}

	public void writeErrorLogs(final ProceedingJoinPoint proceedingJoinPoint, final Exception ex,
			final HttpServletRequest request) {
		final String message = getSourceInfo(proceedingJoinPoint).get(IConstant.AppConstants.METHODNAME)
				+ " Execution caught exception";
		final String stack = Arrays.toString(ex.getStackTrace());
		logger.write(IConstant.AppConstants.LEVELERROR, LoggerHelper.getCorrelationId(request), getUserInfo(request), "9001",
				ex.getMessage(), message, stack, null);
	}

	private String getRequestUrl(final HttpServletRequest request) {
		String requestUrl = null;
		if (Objects.nonNull(request)) {
			final StringBuilder url = new StringBuilder(128);
			url.append(request.getMethod()).append(" : ");
			url.append(request.getRequestURL());
			if (!StringUtils.isEmpty(request.getQueryString())) {
				url.append("?").append(request.getQueryString());
			}
			requestUrl = url.toString();
		}
		return requestUrl;
	}

	private String getRequestPayLoad(final HttpServletRequest request) {
		String payload = "";
		try {
			ContentCachingRequestWrapper wrapper = null;
			if (Objects.nonNull(request)) {
				wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
			}
			if (Objects.nonNull(wrapper)) {
				final byte[] buf = wrapper.getContentAsByteArray();
				if (buf.length > 0) {
					payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
				}
			}
		} catch (final Exception e) {
			payload = "[unknown]";
		}
		return payload;
	}

	public JSONObject getUserInfo(final HttpServletRequest request) {
		JSONObject info = null;
		if (Objects.nonNull(request)) {
			final String token = request.getHeader(IConstant.AppConstants.AUTHORIZATION);
			if (!StringUtils.isEmpty(token)) {
				final String[] tokenType = token.split(" ");
				if (!tokenType[0].toLowerCase().equals("bearer")) {
					return info;
				}
				info = TokenDecode.getUserInfo(tokenType[1]);
			}
		}
		return info;
	}

	public static void loadCommonSettings() throws IOException {
		final OS myOS = OS.getOs();
		final String hostName = InetAddress.getLocalHost().getHostName();
		final String hostId = getUniqueIpaddress();
		final String osName = myOS.getPlatformName();

		LogApplicationSettings.setDeviceType(IConstant.AppConstants.DEVICETYPEVALUE);
		LogApplicationSettings.setHostId(hostId);
		LogApplicationSettings.setHostName(hostName);
		LogApplicationSettings.setOsName(osName.substring(0, osName.indexOf(" ")));
		LogApplicationSettings.setOsVersion(osName.substring(osName.indexOf(" ") + 1).trim());
		LogApplicationSettings.setOffset(getOffset());
	}

	@SuppressWarnings("unchecked")
	private JSONObject getTraceInfoStart(final JSONObject traceInfo, final long startTime) {
		traceInfo.put(IConstant.AppConstants.LOGGINGPOINT, IConstant.AppConstants.LOGGINGPOINTSTART);
		traceInfo.put(IConstant.AppConstants.DURATION, "0");
		// traceInfo.put(AppConstants.METHODTIME, df.format(startTime));
		return traceInfo;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getTraceInfoEnd(final JSONObject traceInfo, final long startTime, final long endTime) {
		traceInfo.put(IConstant.AppConstants.LOGGINGPOINT, IConstant.AppConstants.LOGGINGPOINTEND);
		traceInfo.put(IConstant.AppConstants.DURATION, endTime - startTime);
		return traceInfo;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getSourceInfo(final ProceedingJoinPoint proceedingJoinPoint) {
		final JSONObject sourceInfo = new JSONObject();
		final Signature signature = proceedingJoinPoint.getSignature();
		sourceInfo.put(IConstant.AppConstants.CLASSNAME, signature.getDeclaringTypeName());
		sourceInfo.put(IConstant.AppConstants.METHODNAME, signature.getName());
		return sourceInfo;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getCallerInfo(final ProceedingJoinPoint proceedingJoinPoint) {
		final JSONObject callerInfo = new JSONObject();
		try {
			final HttpServletRequest request = getCurrentRequest();
			final String parentCallerClass = LoggerHelper.getParentCallerClass(request, proceedingJoinPoint);
			final String parentCallerMethod = LoggerHelper.getParentCallerMethod(request, proceedingJoinPoint);
			callerInfo.put(IConstant.AppConstants.NAME, request.getContextPath().substring(1));
			callerInfo.put(IConstant.AppConstants.CLASSNAME, parentCallerClass);
			callerInfo.put(IConstant.AppConstants.METHODNAME, parentCallerMethod);
		} catch (final Exception ex) {
			// no need for logging.
		}
		return callerInfo;
	}

	public HttpServletRequest getCurrentRequest() {
		HttpServletRequest request = null;
		try {
			request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		} catch (final Exception ex) {
			// no need for logging.
		}
		return request;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getTraceInfo(final ProceedingJoinPoint proceedingJoinPoint) {
		final JSONObject traceInfo = new JSONObject();
		traceInfo.put(IConstant.AppConstants.CALLERINFO, getCallerInfo(proceedingJoinPoint));
		traceInfo.put(IConstant.AppConstants.SOURCEINFO, getSourceInfo(proceedingJoinPoint));

		getAdditionalProperties(traceInfo);
		return traceInfo;
	}

	private static String getUniqueIpaddress() throws SocketException {
		final Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
		while (e.hasMoreElements()) {
			final NetworkInterface n = e.nextElement();
			if (n.isUp()) {
				final Enumeration<InetAddress> ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					final InetAddress i = ee.nextElement();
					if (i.isSiteLocalAddress()) {
						return i.getHostAddress();
					}
				}
			}
		}
		return null;
	}

	private static String getOffset() {
		final TimeZone tz1 = TimeZone.getTimeZone(TimeZone.getDefault().getID());
		final long hours = TimeUnit.MILLISECONDS.toHours(tz1.getRawOffset());
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tz1.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
		minutes = Math.abs(minutes);
		if (hours > 0) {
			return String.format("+%d:%02d", hours, minutes);
		} else {
			return String.format("%d:%02d", hours, minutes);
		}
	}

	private void getAdditionalProperties(final JSONObject jsonObject) {

		final HttpServletRequest request = getCurrentRequest();
		if (Objects.nonNull(request)) {
			jsonObject.put(IConstant.AppConstants.X_CORRELATION_ID,
					LoggerHelper.getAttributeValue(request, IConstant.AppConstants.X_CORRELATION_ID));
			jsonObject.put(IConstant.AppConstants.X_CONVERSATION_ID,
					LoggerHelper.getAttributeValue(request, IConstant.AppConstants.X_CONVERSATION_ID));
		}
	}
}
