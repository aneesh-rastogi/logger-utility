package common.logging.aop;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import common.logging.utils.LogUtils;

public class LogsFormat extends Layout {

	private JSONObject deviceInfo;
	private Logger rootLogger;
	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

	static {
		try {

			LogUtils.loadCommonSettings();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public LogsFormat() {
		try {
			deviceInfo = getDeviceInfo();
			rootLogger = Logger.getRootLogger();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a layout that optionally inserts location information into log
	 * messages.
	 *
	 */

	@SuppressWarnings("unchecked")
	public String format(LoggingEvent loggingEvent) {
		try {
			JSONObject logstashEvent = setLogProperties(loggingEvent);
			assert logstashEvent != null;
			logstashEvent.put(IConstant.AppConstants.DEVICEINFO, deviceInfo);
			return logstashEvent + "\n";
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		return "\n";
	}

	@SuppressWarnings("unchecked")
	private JSONObject setLogProperties(LoggingEvent loggingEvent) {
		String message  = "";
		if(loggingEvent.getMessage() instanceof String)
			message = (String) loggingEvent.getMessage();
		if(loggingEvent.getMessage() instanceof StringBuilder)
			message = ((StringBuilder) loggingEvent.getMessage()).toString();
		if (message != null) {
			try {
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(message);
				if (!json.containsKey(IConstant.AppConstants.SEVERITY))
					json.put(IConstant.AppConstants.SEVERITY, rootLogger.getLevel().toString());
				if (LogApplicationSettings.componentVersion != null)
					json.put(IConstant.AppConstants.COMPONENTVERSION, LogApplicationSettings.componentVersion);
				if (LogApplicationSettings.componentName != null)
					json.put(IConstant.AppConstants.COMPONENTNAME, LogApplicationSettings.componentName);
				if (LogApplicationSettings.hostName != null)
					json.put(IConstant.AppConstants.HOSTNAME, LogApplicationSettings.hostName);
				if (LogApplicationSettings.offset != null)
					json.put(IConstant.AppConstants.OFFSET, LogApplicationSettings.offset);
				json.put(IConstant.AppConstants.TIMESTAMP, df.format(loggingEvent.timeStamp));
				json.put(IConstant.AppConstants.CORRELATIONID, json.get(IConstant.AppConstants.CORRELATIONID));
				return json;
			} catch (ParseException e) {
				JSONObject logstashEvent = new JSONObject();
				logstashEvent.put(IConstant.AppConstants.SEVERITY, rootLogger.getLevel().toString());
				if (LogApplicationSettings.componentVersion != null)
					logstashEvent.put(IConstant.AppConstants.COMPONENTVERSION, LogApplicationSettings.componentVersion);
				if (LogApplicationSettings.componentName != null)
					logstashEvent.put(IConstant.AppConstants.COMPONENTNAME, LogApplicationSettings.componentName);
				if (LogApplicationSettings.hostName != null)
					logstashEvent.put(IConstant.AppConstants.HOSTNAME, LogApplicationSettings.hostName);
				if (LogApplicationSettings.offset != null)
					logstashEvent.put(IConstant.AppConstants.OFFSET, LogApplicationSettings.offset);
				logstashEvent.put(IConstant.AppConstants.TIMESTAMP, df.format(loggingEvent.timeStamp));
				ThrowableInformation throwableInformation = loggingEvent.getThrowableInformation();
				if (throwableInformation != null) {
					Throwable throwable = throwableInformation.getThrowable();
					throwable.getStackTrace();
					JSONObject jsonObject = new JSONObject();

					String stack = Arrays.toString(e.getStackTrace());
					jsonObject.put(IConstant.AppConstants.STACKTRACE, stack);
					logstashEvent.put(IConstant.AppConstants.STACKTRACE, jsonObject);
					logstashEvent.put(IConstant.AppConstants.MESSAGECODE, "9001");
					logstashEvent.put(IConstant.AppConstants.MESSAGE, message);
					logstashEvent.put(IConstant.AppConstants.MESSAGEDETAIL, message);
				} else {
					logstashEvent.put(IConstant.AppConstants.MESSAGECODE, "9999");
					logstashEvent.put(IConstant.AppConstants.MESSAGE, message);
					logstashEvent.put(IConstant.AppConstants.MESSAGEDETAIL, message);
				}
				return logstashEvent;
			}
		}
		return null;
	}

	public boolean ignoresThrowable() {
		return false;
	}

	public void activateOptions() {
	}

	@SuppressWarnings("unchecked")
	private static JSONObject getDeviceInfo() throws IOException {
		JSONObject deviceInfo = new JSONObject();
		deviceInfo.put(IConstant.AppConstants.DEVICETYPE, IConstant.AppConstants.DEVICETYPEVALUE);
		deviceInfo.put(IConstant.AppConstants.NAME, LogApplicationSettings.getHostName());
		deviceInfo.put(IConstant.AppConstants.DEVICEID, LogApplicationSettings.getHostId());
		deviceInfo.put(IConstant.AppConstants.OPERATINGSYSTEM, LogApplicationSettings.getOsName());
		deviceInfo.put(IConstant.AppConstants.OPERATINGSYSTEMVERSION, LogApplicationSettings.getOsVersion());
		return deviceInfo;
	}
}