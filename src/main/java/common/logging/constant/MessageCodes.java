package common.logging.constant;

/**
 * <b>Class: </b>MessageCodes
 * <p> 
 * <b>Description: </b>Used to define widely used constants.
 * </p>
 */
public final class MessageCodes {

	/**
	 * Constant APPLICATION_STARTED.
	 * <p>
	 * <b>Code: </b><code>1001</code> </br>
	 * <b>Message: </b>Application/Component has been started. </br>
	 * <b>Description: </b>Message code for service start up.
	 * </p>
	 */
	public static final String APPLICATION_STARTED = "1001";

	/**
	 * Constant APPLICATION_STOPPED.
	 * <p>
	 * <b>Code: </b><code>1002</code> </br>
	 * <b>Message: </b>Application/Component has been stopped. </br>
	 * <b>Description: </b>Message code for service stop.
	 * </p>
	 */
	public static final String APPLICATION_STOPPED = "1002";

	/**
	 * Constant SERVICE_NOT_AVAILABLE.
	 * <p>
	 * <b>Code: </b><code>1004</code> </br>
	 * <b>Message: </b>Service not available. </br>
	 * <b>Description: </b>Message code will be used if the requested service is not available.
	 * </p>
	 */
	public static final String SERVICE_NOT_AVAILABLE = "1004";

	/**
	 * Constant DATABASE_CONNECTION_FAILED.
	 * <p>
	 * <b>Code: </b><code>1005</code> </br>
	 * <b>Message: </b>Database connection failed. </br>
	 * <b>Description: </b>Message code will be used if the requested DB is not available or the connection failed.
	 * </p>
	 */
	public static final String DATABASE_CONNECTION_FAILED = "1005";

	/**
	 * Constant INFORMATIVE_MESSAGE.
	 * <p>
	 * <b>Code: </b><code>1006</code> </br>
	 * <b>Message: </b>Informative message. </br>
	 * <b>Description: </b>Message Code for any information that component can log.
	 * </p>
	 */
	public static final String INFORMATIVE_MESSAGE = "1006";

	/**
	 * Constant DYNAMIC_MESSAGE.
	 * <p>
	 * <b>Code: </b><code>9999</code> </br>
	 * <b>Message: </b>Undefined/dynamic messages. </br>
	 * <b>Description: </b>Undefined messages.
	 * </p>
	 */
	public static final String DYNAMIC_MESSAGE = "9999";

	/**
	 * Constant HANDLED_EXCEPTION.
	 * <p>
	 * <b>Code: </b><code>9000</code> </br>
	 * <b>Message: </b>Handled Exceptions. </br>
	 * <b>Description: </b>Exceptions which has been handled in try/catch block.
	 * </p>
	 */
	public static final String HANDLED_EXCEPTION = "9000";

	/**
	 * Constant UNHANDLED_EXCEPTION.
	 * <p>
	 * <b>Code: </b><code>9001</code> </br>
	 * <b>Message: </b>Unhandled exceptions. </br>
	 * <b>Description: </b>Unhandled exceptions.
	 * </p>
	 */
	public static final String UNHANDLED_EXCEPTION = "9001";

	/**
	 * Constant INCOMING_HTTP_REQUEST.
	 * <p>
	 * <b>Code: </b><code>5001</code> </br>
	 * <b>Message: </b>Incoming http request. </br>
	 * <b>Description: </b>Incoming http calls that comes to a service.
	 * </p>
	 */
	public static final String INCOMING_HTTP_REQUEST = "5001";

	/**
	 * Constant OUTGOING_HTTP_RESPONSE.
	 * <p>
	 * <b>Code: </b><code>5002</code> </br>
	 * <b>Message: </b>Outgoing http response. </br>
	 * <b>Description: </b>Response retuned to the caller of a service.
	 * </p>
	 */
	public static final String OUTGOING_HTTP_RESPONSE = "5002";

	/**
	 * Constant OUTGOING_HTTP_REQUEST.
	 * <p>
	 * <b>Code: </b><code>5003</code> </br>
	 * <b>Message: </b>Outgoing http request. </br>
	 * <b>Description: </b>Request sent to another service.
	 * </p>
	 */
	public static final String OUTGOING_HTTP_REQUEST = "5003";

	/**
	 * Constant INCOMING_HTTP_RESPONSE.
	 * <p>
	 * <b>Code: </b><code>5004</code> </br>
	 * <b>Message: </b>Incoming http response. </br>
	 * <b>Description: </b>Response received from another service.
	 * </p>
	 */
	public static final String INCOMING_HTTP_RESPONSE = "5004";

	/**
	 * Constant BACKGROUND_PROCESS_STARTED.
	 * <p>
	 * <b>Code: </b><code>2001</code> </br>
	 * <b>Message: </b>Background Process Started	 </br>
	 * <b>Description: </b>For All Background jobs and Deamon.
	 * </p>
	 */
	public static final String BACKGROUND_PROCESS_STARTED = "2001";

	/**
	 * Constant BACKGROUND_PROCESS_ENDED.
	 * <p>
	 * <b>Code: </b><code>2002</code> </br>
	 * <b>Message: </b>Background Process Ended </br>
	 * <b>Description: </b>For All Background jobs and Deamon.
	 * </p>
	 */
	public static final String BACKGROUND_PROCESS_ENDED = "2002";

	/**
	 * Constant BACKGROUND_PROCESS_PROCESSING.
	 * <p>
	 * <b>Code: </b><code>2003</code> </br>
	 * <b>Message: </b>Background Job Inside Processing info. </br>
	 * <b>Description: </b>Background Job Inside Processing info.
	 * </p>
	 */
	public static final String BACKGROUND_PROCESS_PROCESSING = "2003";

	/**
	 * Constant WEB_APP_PROCESSING.
	 * <p>
	 * <b>Code: </b><code>2004</code> </br>
	 * <b>Message: </b>Web app Internal process info </br>
	 * <b>Description: </b>Web app Internal process info.
	 * </p>
	 */
	public static final String WEB_APP_PROCESSING = "2004";

	/**
	 * Constant PREDEFINE_LIMIT_EXCEEDED.
	 * <p>
	 * <b>Code: </b><code>2005</code> </br>
	 * <b>Message: </b>Predefine limit exceeded </br>
	 * <b>Description: </b>Predefine limit Exceeded.
	 * </p>
	 */
	public static final String PREDEFINE_LIMIT_EXCEEDED = "2005";

	/**
	 * Private constructor for avoiding instantiation.
	 */
	private MessageCodes() {
	}

}
