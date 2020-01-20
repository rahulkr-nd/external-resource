package com.netradyne.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersResult;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.netradyne.enums.AppMode;

public class jwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(jwtUtil.class);

	public Map<String, Integer> TokenVerification(String token) throws JSONException {

		Map<String, Integer> output = new HashMap<String, Integer>();
		String secretKey = GetSecretKey(Constants.ENV, Constants.SSM_KEY);
		if (token == null) {
			output.put("response", 1);
			return output;
		}
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			JWTVerifier verifier = JWT.require(algorithm).acceptExpiresAt(0).build();
			DecodedJWT jwt = verifier.verify(token);
		} catch (JWTVerificationException exception) {
			if (exception instanceof TokenExpiredException) {
				logger.info(exception.toString());
				output.put("response", 1);
				return output;
			} else if (exception instanceof JWTDecodeException) {
				logger.info(exception.toString());
				output.put("response", 2);
				return output;
			}
		}
		output.put("response", 3);
		return output;
	}

	public String Claims(String token, String customer) throws JSONException {
		DecodedJWT jwt = JWT.decode(token);
		Map<String, Claim> claims = jwt.getClaims();
		logger.info(claims.toString());
		try {
			if (customer.toLowerCase().indexOf("partner") != -1
					&& claims.get("partner_resource_accessible").asBoolean()) {
				return "partner";
			} else if (customer.toLowerCase().indexOf("installer") != -1
					&& claims.get("installer_resource_accessible").asBoolean()) {
				return "installer";
			} else {
				logger.info("installer or partner information is missing!");
				return null;
			}
		} catch (Exception e) {
			logger.info("Invalid resource.");
			return null;
		}
	}

	public String createtoken() {
		String[] array = new String[2];
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		try {
			json1.put("name", "can access partner resources");
			json1.put("code", "can.access-installer-resources");
			json2.put("name", "can login");
			json2.put("code", "can.login");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		array[0] = json1.toString();
		array[1] = json2.toString();
		Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET_KEY);
		String token = JWT.create().withClaim("email", "batman@netradyne.com")
				.withClaim("jti", "0b02a383-fd68-4098-afcc-42a9620697a9").withClaim("username", "partner1")
				.withArrayClaim("permissions", array)
				.withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 3600 * 1000)).withIssuer("auth0")
				.sign(algorithm);
		return token;

	}

	public JSONObject readAllCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		JSONObject json = null;
		if (cookies != null) {
			String a = "{" + Arrays.stream(cookies).map(c -> c.getName() + ":" + c.getValue())
					.collect(Collectors.joining(", ")) + "}";
			try {
				json = new JSONObject(a);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (json.has(Constants.COOKIE_KEY)) {
				try {
					return new JSONObject().put(Constants.COOKIE_KEY, json.get(Constants.COOKIE_KEY));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				logger.info("jwt key is missing in cookies");
			}
		}
		return new JSONObject();
	}

	public URL generatePreSignUrl(String customer) {
		String filename = null;
		if (customer.equals(Constants.PARTNER)) {
			filename = Constants.PARTNER_KEY;
		} else if (customer.equals(Constants.INSTALLER)) {
			filename = Constants.INSTALLER_KEY;
		}
		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Constants.CLIENT_REGION)
					.withCredentials(new ProfileCredentialsProvider()).build();

			Date expiration = new java.util.Date();
			long expTimeMillis = expiration.getTime();
			expTimeMillis += Constants.URL_EXPIRATION_TIME;
			expiration.setTime(expTimeMillis);
			logger.info("Generating pre-signed URL.");
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
					Constants.BUCKET_NAME, filename).withMethod(HttpMethod.GET).withExpiration(expiration);
			URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

			logger.info("Pre-Signed URL: " + url.toString());
			return url;
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static AWSCredentialsProviderChain getAWSCredentialsProviderChain(String environment) {
		List<AWSCredentialsProvider> awsCredentialsProviders = (AppMode
				.valueOf(environment.toUpperCase()) != AppMode.DEVELOPMENT) ? Collections.unmodifiableList(
						Arrays.asList(new InstanceProfileCredentialsProvider(true), new ProfileCredentialsProvider()))
						: Collections.unmodifiableList(Arrays.asList(new ProfileCredentialsProvider()));
		return new AWSCredentialsProviderChain(awsCredentialsProviders);
	}

	public static List<String> getSSMObj(String key, String environment) {
		GetParametersRequest parametersRequest = new GetParametersRequest();

		GetParametersResult parametersResult = AWSSimpleSystemsManagementClientBuilder.standard()
				.withCredentials(getAWSCredentialsProviderChain(environment)).withRegion(Regions.US_WEST_1).build()
				.getParameters(parametersRequest.withNames(key).withWithDecryption(true));
		if (parametersResult != null) {
			return parametersResult.getParameters().stream()
					.filter(parameter -> parameter.getName().equalsIgnoreCase(key))
					.map(parameter -> parameter.getValue()).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	public static String GetSecretKey(String environment, String key) throws JSONException {
		String secretKey = null;
		List<String> ssmObj = getSSMObj(key, environment);
		if (environment.equalsIgnoreCase("development")) {
			secretKey = System.getenv("secret_key");
		} else {
			if ((ssmObj != null) && (!ssmObj.isEmpty())) {
				secretKey = ssmObj.get(0);
			}
		}
		return secretKey;
	}

	public static void main(String args[]) throws JSONException {

		jwtUtil jwtutil = new jwtUtil();
		System.out.println(jwtutil.GetSecretKey("staging", Constants.SSM_KEY));
		// System.out.println(jwtutil.createtoken());
		//
		// System.out.println(jwtutil.TokenVerification(jwtutil.createtoken(),
		// Constants.SECRET_KEY));
		// System.out.println(jwtutil.Claims(Constants.ENCODED,"installer"));
	}
}
