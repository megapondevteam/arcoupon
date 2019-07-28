package com.megapon.arcoupon.common.header;

import org.springframework.stereotype.Component;

@Component
public class HeaderContext {
	private static final ThreadLocal<String> authToken = new ThreadLocal<String>();
	private static final ThreadLocal<String> transId = new ThreadLocal<String>();
	private static final ThreadLocal<String> userId = new ThreadLocal<String>();

	public static String getAuthToken() {
		return authToken.get();
	}

	public static void setAuthToken(String atoken) {
		authToken.set(atoken);
	}

	public static String getTransId() {
		return transId.get();
	}

	public static void setTransId(String tid) {
		transId.set(tid);
	}

	public static String getUserId() {
		return userId.get();
	}

	public static void setUserId(String aUser) {
		userId.set(aUser);
	}
}