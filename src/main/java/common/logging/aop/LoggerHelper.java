package common.logging.aop;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class LoggerHelper {

	public static HttpHeaders setHeaders() {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		final String correlationId = getCorrelationId(request);

		final HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set(IConstant.AppConstants.CORRELATIONID, correlationId);
		requestHeaders.set(IConstant.AppConstants.AUTHORIZATION, request.getHeader(IConstant.AppConstants.AUTHORIZATION));
		return requestHeaders;
	}

	public static HttpHeaders setHeaders(final String callerServiceName, final String currentClassName,
			final String currentMethodName) {
		final HttpHeaders requestHeaders = setHeaders();
		try {
			requestHeaders.set("parentCallerClass", currentClassName);
			requestHeaders.set("parentCallerMethod", currentMethodName);
			requestHeaders.set("serviceName", callerServiceName);
		} catch (final Exception ex) {
			// no need for logging.
		}
		return requestHeaders;
	}

	public static String getCorrelationId(final HttpServletRequest request) {

		return getAttributeValue(request, IConstant.AppConstants.CORRELATIONID);

	}

	public static String getAttributeValue(final HttpServletRequest request, final String attributeName) {
		String attributeValue = null;
		try {
			if (request != null) {
				attributeValue = (String) request.getAttribute(attributeName);
				if (attributeValue == null) {
					attributeValue = request.getHeader(attributeName);
					if (attributeValue == null) {
						attributeValue = UUID.randomUUID().toString();
					}
					request.setAttribute(attributeName, attributeValue);
				}
			} else {
				attributeValue = MDC.get(attributeName);
			}

		} catch (final Exception ex) {
			// no need for logging.
		}
		return attributeValue;
	}

	public static String getReqResMappingId(final HttpServletRequest request) {
		String reqResMappingId = null;
		try {
			reqResMappingId = (String) request.getAttribute(IConstant.AppConstants.REQRESMAPPINGID);
			if (reqResMappingId == null) {
				reqResMappingId = request.getHeader(IConstant.AppConstants.REQRESMAPPINGID);
			}
		} catch (final Exception ex) {
			// no need for logging.
		}
		return reqResMappingId;
	}

	public static String getParentCallerClass(final HttpServletRequest request,
			final ProceedingJoinPoint proceedingJoinPoint) {
		String parentCallerClass = null;
		if (Objects.nonNull(request)) {
			if (request.getAttribute("parentCallerClass") == null) {
				if (request.getHeader("parentCallerClass") == null) {
					parentCallerClass = "Root";
				} else {
					parentCallerClass = request.getHeader("parentCallerClass");
				}
			} else {
				parentCallerClass = (String) request.getAttribute("parentCallerClass");
			}
			request.setAttribute("parentCallerClass", proceedingJoinPoint.getSignature().getDeclaringTypeName());
		}
		return parentCallerClass;
	}

	public static String getParentCallerMethod(final HttpServletRequest request,
			final ProceedingJoinPoint proceedingJoinPoint) {
		String parentCallerMethod = null;
		if (Objects.nonNull(request)) {
			if (request.getAttribute("parentCallerMethod") == null) {
				if (request.getHeader("parentCallerMethod") == null) {
					parentCallerMethod = "Root";
				} else {
					parentCallerMethod = request.getHeader("parentCallerMethod");
				}
			} else {
				parentCallerMethod = (String) request.getAttribute("parentCallerMethod");
			}
			request.setAttribute("parentCallerMethod", proceedingJoinPoint.getSignature().getName());
		}
		return parentCallerMethod;
	}

	public static String getParentServiceName(final HttpServletRequest request,
			final ProceedingJoinPoint proceedingJoinPoint) {
		String parentServiceName = null;
		if (Objects.nonNull(request)) {
			if (request.getAttribute("serviceName") == null) {
				if (request.getHeader("serviceName") == null) {
					parentServiceName = "Root";
				} else {
					parentServiceName = request.getHeader("serviceName");
				}
			} else {
				parentServiceName = (String) request.getAttribute("serviceName");
			}
			request.setAttribute("serviceName", proceedingJoinPoint.getSignature().getName());
		}
		return parentServiceName;
	}

	public static void setRequestInfo(String correlationId, final String source, final String context) {
		if (StringUtils.isEmpty(correlationId)) {
			correlationId = UUID.randomUUID().toString();
		}
		MDC.put(IConstant.AppConstants.CORRELATIONID, correlationId);

		if (source == null) {
			MDC.remove(IConstant.AppConstants.SOURCE);
		} else {
			MDC.put(IConstant.AppConstants.SOURCE, source);
		}

		if (context == null) {
			MDC.remove(IConstant.AppConstants.CONTEXT);
		} else {
			MDC.put(IConstant.AppConstants.CONTEXT, context);
		}
	}

	public static void setRequestInfo(final String correlationId, final String source, final String clientId,
			final String context) {
		setRequestInfo(correlationId, source, context);
		if (clientId == null) {
			MDC.remove(IConstant.AppConstants.CLIENTID);
		} else {
			MDC.put(IConstant.AppConstants.CLIENTID, clientId);
		}
	}
}
