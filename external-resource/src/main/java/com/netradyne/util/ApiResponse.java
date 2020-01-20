package com.netradyne.util;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ApiResponse
 */
@JsonAutoDetect
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(ApiResponse.class);
	private static final long serialVersionUID = 4428041083389685548L;
	private boolean response;
	private String error;
	private String status;
	private String msg;
	private transient Map<String, Object> data;
	private CacheControl cacheControl = null;

	@JsonInclude(Include.NON_NULL)
	private String msgCode;

	public static final String MESSAGE_BAD_REQUEST = "Bad Request.";
	public static final String MESSAGE_NOT_FOUND = "Not found.";
	public static final String MESSAGE_DATA_CAPTURED = "Data Captured";
	public static final String MESSAGE_EMPTY_DATA = "Data is empty.";
	public static final String MESSAGE_FAILED_TO_UPDATE = "Failed to update the details.";
	public static final String MESSAGE_INVALID_DATA = "Invalid data.";
	public static final String MESSAGE_INVALID_KEY = "Bad request, Invalid key.";
	public static final String MESSAGE_INVALID_PAYLOAD = "Bad request. Invalid payload.";
	public static final String MISSING_PARAMS = "Missing Required Params";
	public static final String INVALID_PARAMS = "Invalid params";
	public static final String INVALID_DATE_FORMAT = "Invalid date format";
	public static final String MESSAGE_NOT_AUTHORIZED = "Bad Request, You're not Authorized.";
	public static final String MESSAGE_NOT_AUTHORIZED_OPERATION = "Bad Request, You're not Authorized to perform this Operation.";
	public static final String MESSAGE_QUERY_PARAMS_MISSING = "Query params missing.";
	public static final String MESSAGE_SUCCESSFULLY_UPDATED = "Successfully updated.";
	public static final String MESSAGE_UNKNOWN_ERROR = "Bad request. Something went wrong.";
	public static final String MESSAGE_DEVICE_NOT_FOUND = "Device not found";
	public static final String MESSAGE_INVALID_JSON = "Invalid JSON payload";
	public static final String MESSAGE_INVALID_VEHICLE_GVWR = "Vehicle GVWR should be in formatted eg. <weight> lb or <weight> kg";
	public static final String DRIVER_LOGIN_PERMISSION_ERROR = "Driver can login only through App.";
	public static final String DRIVER_APP_LOGIN_PERMISSION_ERROR = "Only driver can login through App.";
	public static final String DATAPLATFORM_LOGIN_PERMISSION_ERROR = "You can only login in Dataplatform.";
	public static final String SESSION_TIMEDOUT = "Your session has timed out. Please login again.";

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public ApiResponse setMsgCode(String code) {
		this.msgCode = code;
		return this;
	}

	public void setCacheControl(boolean noCache, boolean noStore, int maxAge) {
		cacheControl = new CacheControl();
		cacheControl.setNoCache(noCache);
		cacheControl.setNoStore(noStore);
		cacheControl.setMaxAge(maxAge);
	}

	/**
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		if (this.data == null) {
			return null;
		}
		return data.get(key);
	}

	/**
	 * @param key
	 * @param obj
	 */
	public void add(String key, Object obj) {
		if (data == null) {
			data = new HashMap<>();
		}
		data.put(key, obj);
	}

	/**
	 * @param content
	 */
	public void addAll(Map<String, Object> content) {
		if (content == null) {
			return;
		}
		if (data == null) {
			data = new HashMap<>();
		}
		data.putAll(content);
	}

	/**
	 * @return
	 */
	public Map<String, Object> auditProperties() {
		Map<String, Object> obj = new HashMap<>();
		obj.put("message", msg);
		obj.put("data", data);
		obj.put("response", response);
		return obj;
	}

	@Override
	public String toString() {
		return String.format("ApiResponse [response=%s, error=%s, status=%s, msg=%s, data=%s]", response, error, status,
				msg, data);
	}

	/**
	 * @param message
	 * @return
	 */
	public Response errorResponse(String message) {
		return errorResponse(message, Status.BAD_REQUEST);
	}

	/**
	 * @param message
	 * @param statusCode
	 * @return
	 */
	public Response errorResponse(String message, Status statusCode) {
		setResponse(false);
		if (message != null) {
			setMsg(message);
		}
		logger.info("errorResponse: {}", message);
		return Response.status(statusCode).entity(this).build();
	}

	public Response error(Response response) {
		setResponse(false);
		logger.info("errorResponse: {}", response.getStatusInfo().getReasonPhrase());
		return Response.status(response.getStatus()).entity(this).build();
	}

	/**
	 * @return
	 */
	public Response successResponse() {
		if (cacheControl != null) {
			return Response.status(Status.OK).cacheControl(cacheControl).entity(this).build();
		} else {
			return Response.status(Status.OK).entity(this).build();
		}
	}

}
