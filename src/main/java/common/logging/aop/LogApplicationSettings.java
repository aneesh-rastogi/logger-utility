package common.logging.aop;

public final class LogApplicationSettings {

	public static  String deviceType;
	public static String hostName;
	public static String hostId;
	public static String componentVersion;
	public static String osName;
	public static String osVersion;
	public static String offset;
	public static String componentName;
	public static String getComponentName() {
		return componentName;
	}
	public static void setComponentName(String componentName) {
		LogApplicationSettings.componentName = componentName;
	}
	public static void setDeviceType(String deviceType) {
		LogApplicationSettings.deviceType = deviceType;
	}
	public static void setHostName(String hostName) {
		LogApplicationSettings.hostName = hostName;
	}
	public static void setHostId(String hostId) {
		LogApplicationSettings.hostId = hostId;
	}
	public static void setComponentVersion(String componentVersion) {
		LogApplicationSettings.componentVersion = componentVersion;
	}
	public static void setOsName(String osName) {
		LogApplicationSettings.osName = osName;
	}
	public static void setOsVersion(String osVersion) {
		LogApplicationSettings.osVersion = osVersion;
	}
	public static void setOffset(String offset) {
		LogApplicationSettings.offset = offset;
	}
	public static String getDeviceType() {
		return deviceType;
	}
	public static String getHostName() {
		return hostName;
	}
	public static String getHostId() {
		return hostId;
	}
	public static String getComponentVersion() {
		return componentVersion;
	}
	public static String getOsName() {
		return osName;
	}
	public static String getOsVersion() {
		return osVersion;
	}
	public static String getOffset() {
		return offset;
	}	
}
