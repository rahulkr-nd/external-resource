package com.netradyne.util;

import com.amazonaws.regions.Regions;

public class Constants {

	public static final String SSM_KEY = "resource_service_jwt_sso_key";
	public static final String ENV = "production";

	// TESTING
	public static final String ENCODED = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1Nzg5MTI3MTEsImV4cCI6MTU3OTUxNzUxMSwianRpIjoiNzUxYTRhMDgtYmUxOC00NDE1LWJhZDctMjNmMTM2OWE0NTVmIiwiZW1haWwiOiJnYXVyYXYuYWdhcndhbEBuZXRyYWR5bmUuY29tIiwibmFtZSI6Im9wZXJhdGlvbnMgY29vcmRpbmF0b3IiLCJ1c2VybmFtZSI6Im9wc2NvIiwicGFydG5lcl9yZXNvdXJjZV9hY2Nlc3NpYmxlIjpmYWxzZSwiaW5zdGFsbGVyX3Jlc291cmNlX2FjY2Vzc2libGUiOnRydWV9.2UaP8QYHPswD7l98Zhd3b4QyDyPhIRdjszJ2wYNO9zc";
	public static final String IN_ENCODED = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1NzcwOTk5NjEsImV4cCI6MTU4NzcwNDc2MSwianRpIjoiMGIwMmEzODMtZmQ2OC00MDk4LWFmY2MtNDJhOTYyMDY5N2E5IiwiZW1haWwiOiJnYXVyYXYuYWdhcndhbEBuZXRyYWR5bmUuY29tIiwibmFtZSI6IkdhdXJhdiAiLCJ1c2VybmFtZSI6InBhcnRuZXIxIiwicGVybWlzc2lvbnMiOlt7Im5hbWUiOiJjYW4gYWNjZXNzIGluc3RhbGxlciByZXNvdXJjZXMiLCJjb2RlIjoiY2FuLmFjY2Vzcy1pbnN0YWxsZXItcmVzb3VyY2VzIn0seyJuYW1lIjoiY2FuIGxvZ2luIiwiY29kZSI6ImNhbi5sb2dpbiJ9XX0.IIJlROTebXmF2IR9fr7T6Kfx99iVI7V4I-KPyFhB7Dw";
	public static final String EX_ENCODED = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1NzcwOTk5NjEsImV4cCI6MTU3NzcwNDc2MSwianRpIjoiMGIwMmEzODMtZmQ2OC00MDk4LWFmY2MtNDJhOTYyMDY5N2E5IiwiZW1haWwiOiJnYXVyYXYuYWdhcndhbEBuZXRyYWR5bmUuY29tIiwibmFtZSI6IkdhdXJhdiAiLCJ1c2VybmFtZSI6InBhcnRuZXIxIiwicGVybWlzc2lvbnMiOlt7Im5hbWUiOiJjYW4gYWNjZXNzIHBhcnRuZXIgcmVzb3VyY2VzIiwiY29kZSI6ImNhbi5hY2Nlc3MtcGFydG5lci1yZXNvdXJjZXMifSx7Im5hbWUiOiJjYW4gbG9naW4iLCJjb2RlIjoiY2FuLmxvZ2luIn1dfQ.5BkAXAw5FZuI-hDczd-4oR8Al0MetssibdiWQ8N0D6g";
	public static final String INVALID_ENCODED = "yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1NzcwOTk5NjEsImV4cCI6MTU3NzcwNDc2MSwianRpIjoiMGIwMmEzODMtZmQ2OC00MDk4LWFmY2MtNDJhOTYyMDY5N2E5IiwiZW1haWwiOiJnYXVyYXYuYWdhcndhbEBuZXRyYWR5bmUuY29tIiwibmFtZSI6IkdhdXJhdiAiLCJ1c2VybmFtZSI6InBhcnRuZXIxIiwicGVybWlzc2lvbnMiOlt7Im5hbWUiOiJjYW4gYWNjZXNzIHBhcnRuZXIgcmVzb3VyY2VzIiwiY29kZSI6ImNhbi5hY2Nlc3MtcGFydG5lci1yZXNvdXJjZXMifSx7Im5hbWUiOiJjYW4gbG9naW4iLCJjb2RlIjoiY2FuLmxvZ2luIn1dfQ.5BkAXAw5FZuI-hDczd-4oR8Al0MetssibdiWQ8N0D6g";
	public static final String SECRET_KEY = "KUYgVj6mFm4mb4Z4jF6M4BPsL5h_APWIiRIuwNx_iWwnlwnlwfiweklmepkeqmkldiojejioejoedjedmkledmkle";
	//

	public static final String INSTALLER_KEY = "Netradyne_Platform_APIs_V_1.0.3.html";
	public static final String PARTNER_KEY = "partner-login.pdf";
	public static final String INSTALLER = "installer";
	public static final String PARTNER = "partner";
	public static final long EXPIRE_DURATION = 10000;
	public static final String BUCKET_NAME = "event-streaming-testing";
	public static final Regions CLIENT_REGION = Regions.US_WEST_1;
	public static final long URL_EXPIRATION_TIME = 1000 * 10;
	public static final String COOKIE_KEY = "resource_id";

	// URL
	public static final String IDMS_LOGIN_URL = "https://idms.netradyne.com/console/#/login?redirectUrl=%2F";
	public static final String NETRADYNE_HOMEPAGE = "https://www.netradyne.com/";
}