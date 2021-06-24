package common.logging.controller;

import common.logging.interceptor.Log;
import org.apache.log4j.LogManager;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

	@Autowired
	private Log logger;

	@RequestMapping(value = "/loggingmode/{loglevel}", method = RequestMethod.PUT)
	public ResponseEntity<Void> loggingmode(@PathVariable(value = "loglevel") String logLevel) {
		logger.setLogLevel(logLevel);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/loggingmode", method = RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public ResponseEntity<JSONObject> getLoggingMode() {
		JSONObject json = new JSONObject();
		String serviceLogLevel = "";
		if(logger.getLogLevel() != null) {
			serviceLogLevel = logger.getLogLevel().toString();
		}

		json.put("serviceLogLevel", serviceLogLevel);
		return ResponseEntity.status(HttpStatus.OK).body(json);
	}
}
