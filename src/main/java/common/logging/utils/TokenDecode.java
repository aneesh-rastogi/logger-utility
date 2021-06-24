package common.logging.utils;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class TokenDecodeFilter.
 */
public class TokenDecode {

	private TokenDecode() {
	}

	/** The object mapper. */
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Enable global filter header.
	 *
	 *
	 *
	 * @param tokenValue the token value
	 * @return the JSONbject
	 */

	@SuppressWarnings("unchecked")
	public static JSONObject getUserInfo(final String tokenValue) {
		try {
			final Jwt jwt = JwtHelper.decode(tokenValue);
			final String content = jwt.getClaims();
			final Map<String, Object> map = objectMapper.readValue(content, Map.class);
			if (map.containsKey("user_name")) {
				final JSONObject userJson = new JSONObject();
				userJson.put("userId", map.get("userid"));
				userJson.put("userName", map.get("user_name"));
				userJson.put("appId", map.get("appid"));
				userJson.put("type", map.get("usertype"));
				userJson.put("deviceId", map.get("deviceid"));
				return userJson;
			}

			return null;

		} catch (final Exception e) {
			throw new InvalidTokenException("Cannot convert access token to JSON", e);
		}
	}
}
