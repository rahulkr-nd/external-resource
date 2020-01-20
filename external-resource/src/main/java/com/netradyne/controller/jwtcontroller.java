package com.netradyne.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netradyne.util.Constants;
import com.netradyne.util.jwtUtil;
import com.netradyne.util.pojo;

@RestController
@RequestMapping(path = "")
public class jwtcontroller {
	private static final Logger logger = LoggerFactory.getLogger(jwtcontroller.class);
	private static final jwtUtil jwtutil = new jwtUtil();

	@Consumes("MediaType.APPLICATION_JSON")
	@Produces("MediaType.APPLICATION_JSON")
	@RequestMapping(value = "/access", method = RequestMethod.GET)
	public pojo getresource(HttpServletRequest request, @RequestParam(required = false) String jwt,
			@RequestParam(required = false) String user, HttpServletResponse response) throws JSONException {
		String customer = user;
		String token;
		logger.info(user);
		if (jwt != null) {
			logger.info(jwt.toString());
			token = jwt;
		} else {
			token = null;
		}
		if (!jwtutil.readAllCookies(request).has(Constants.COOKIE_KEY)) {
			logger.info("cookie is empty");
			return tokenVerification(token, response, customer);
		} else {
			token = (String) jwtutil.readAllCookies(request).get(Constants.COOKIE_KEY);
			return tokenVerification(token, null, customer);

		}
	}

	@Consumes("MediaType.APPLICATION_JSON")
	@Produces("MediaType.APPLICATION_JSON")
	@RequestMapping(value = "/access", method = { RequestMethod.POST })
	public pojo getresource(@RequestBody(required = false) String jwt, @RequestParam(required = false) String user,
			HttpServletRequest request, HttpServletResponse response) throws JSONException {
		String customer = user;
		String token;
		if (jwt != null) {
			logger.info(jwt.toString());
			token = new JSONObject(jwt).getString("jwt");
		} else {
			token = null;
		}

		if (!jwtutil.readAllCookies(request).has(Constants.COOKIE_KEY)) {
			logger.info("cookie is empty");
			return tokenVerification(token, response, customer);
		} else {
			token = (String) jwtutil.readAllCookies(request).get(Constants.COOKIE_KEY);
			return tokenVerification(token, null, customer);

		}
	}

	public static pojo tokenVerification(String token, HttpServletResponse response, String customer)
			throws JSONException {
		pojo result = new pojo(0);
		logger.info("Validating the token");
		switch (jwtutil.TokenVerification(token).get("response")) {
		case 1:
			logger.info("token expired");
			result.setId(1);
			return result;
		case 2:
			logger.info("token validation failed!");
			result.setId(2);
			return result;
		case 3:
			logger.info("token validation successfull!");
		}
		if (response != null) {
			response.addCookie(setCookie(token));
			logger.info("cookie saved successfully");
		}
		if (jwtutil.Claims(token, customer) == null) {
			result.setId(4);
			logger.info("Unauthorized access!");
			return result;
		}
		result.setId(3);
		result.setUrl(jwtutil.generatePreSignUrl(jwtutil.Claims(token, customer)).toString());
		logger.info(result.toString());
		return result;

	}

	public static Cookie setCookie(String token) {
		Cookie cookie = new Cookie(Constants.COOKIE_KEY, token);
		return cookie;

	}

	@RequestMapping(value = "/removecookie", method = { RequestMethod.POST })
	public void removeCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(Constants.COOKIE_KEY, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		logger.info("cookie expiration saved to 0!");

	}
}
